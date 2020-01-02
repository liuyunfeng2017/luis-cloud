package com.cloud.luis.gateway.filter;

import java.io.UnsupportedEncodingException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

import org.apache.commons.codec.CharEncoding;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import com.alibaba.fastjson.JSON;
import com.auth0.jwt.JWT;
import com.cloud.luis.common.exception.CustomException;
import com.cloud.luis.common.model.UserActiveLog;
import com.cloud.luis.common.properties.CustomConstants;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

/**
 * 用户操作日志记录
 * 
 * @author luis
 * @date 2020/12/31
 */

@Slf4j
@Component
public class LoggerFilter implements GlobalFilter, Ordered {

    /**
     * 优先级最低
     */
    @Override
    public int getOrder() {
        return Ordered.LOWEST_PRECEDENCE - 100;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        //封装用户日志实体类
        UserActiveLog userLog = new UserActiveLog();
        ServerHttpRequest request = exchange.getRequest();
        String ip = getIpAddress(request);
        String method = request.getMethodValue();
        String uri = request.getURI().getPath();
        LocalDateTime startTime = LocalDateTime.now();
        
        userLog.setMethod(method);
        userLog.setRequestIp(ip);
        userLog.setRequestTime(startTime);
        userLog.setRequestUri(uri);
        
        //获取token
        List<String> tokenList = request.getHeaders().get("Authorization");
        String accessToken = null;
        Long userId = null;
        if(!CollUtil.isEmpty(tokenList)) {
            accessToken = tokenList.get(0);
        }
        //从token中取用户ID
        if(StrUtil.isNotEmpty(accessToken)) {
            userId = JWT.decode(accessToken).getClaim("userId").asLong();
            userLog.setUserId(userId);
        }
        
        log.debug("LoggerFilter ip: {} method: {} uri: {} token {} userId {}", ip, method, uri, accessToken, userId);
        //获取请求报文
        String cacheBody = getCacheBodyStr(exchange);
        if(StrUtil.isNotEmpty(cacheBody)) {
            userLog.setRequestBodyJson(cacheBody);
        }
        
        return chain.filter(exchange).then(Mono.fromRunnable(() -> {
            LocalDateTime endTime = LocalDateTime.now();
            userLog.setResponseTime(endTime);
            userLog.setElapsedTime(Duration.between(startTime,endTime).toMillis());
            //TODO 用户操作日志可写入kafka或是数据库
            log.debug("LoggerFilter filter info {}", JSON.toJSONString(userLog));
        }));
    }
    
    /**
     *  读取CacheBodyFilter过滤器中缓存的请求数据
     * @param exchange
     * @return
     */
    private String getCacheBodyStr(ServerWebExchange exchange) {
        Object cachedBody = exchange.getAttributes().get(CustomConstants.CACHE_REQ_BODY_KEY);
        String cacheBodyStr = null;
        if(ObjectUtil.isNotEmpty(cachedBody)) {
            try {
                byte[] body = (byte[]) cachedBody;
                cacheBodyStr = new String(body, CharEncoding.UTF_8);
            } catch (UnsupportedEncodingException e) {
                throw new CustomException(e.getMessage());
            }
        }
        return cacheBodyStr;
    }

    /**
     * 获取请求IP
     * 
     * @param request
     * @return
     */
    private static String getIpAddress(ServerHttpRequest request) {
        HttpHeaders headers = request.getHeaders();
        String ip = headers.getFirst("x-forwarded-for");
        if (ip != null && ip.length() != 0 && !"unknown".equalsIgnoreCase(ip)) {
            // 多次反向代理后会有多个ip值，第一个ip才是真实ip
            if (ip.indexOf(",") != -1) {
                ip = ip.split(",")[0];
            }
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = headers.getFirst("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = headers.getFirst("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = headers.getFirst("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = headers.getFirst("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = headers.getFirst("X-Real-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddress().getAddress().getHostAddress();
        }
        return ip;
    }

}
