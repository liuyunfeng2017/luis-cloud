package com.cloud.luis.gateway.filter;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.server.ServerWebExchange;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.cloud.luis.common.properties.CustomConstants;
import com.cloud.luis.common.utils.PermitAllUrl;
import com.cloud.luis.gateway.properties.CustomProperties;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

/**
 * 网关全局过滤器，用于校验请求token是否合法
 * @author luis
 * @date 2019/12/18
 */
@Slf4j
public class TokenFilter implements GlobalFilter, Ordered {
	
	@Resource
	private RedisTemplate<String, Object> redisTemplate;
	
	@Autowired
	CustomProperties customProperties;
	
	private AntPathMatcher antPathMatcher = new AntPathMatcher();
	
	@Override
	public int getOrder() {
		return Ordered.LOWEST_PRECEDENCE;
	}

	@Override
	public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
	    ServerHttpRequest request = exchange.getRequest();
	    //过滤放开的URL不做校验
	    List<String> excludeUrls = customProperties.getExcludeUrls();
	    String[] ignoreurlArray = PermitAllUrl.permitAllUrl(excludeUrls);
        for (String url : ignoreurlArray) {
            String reqUri = request.getURI().getPath();
            if(StrUtil.isNotEmpty(url) && antPathMatcher.match(url, reqUri)) {
                log.debug("request uri {}, in excludeUrls, so pass this request... ...", reqUri);
                return chain.filter(exchange);
            }
        }
        
        //获取token
		List<String> tokenList = exchange.getRequest().getHeaders().get("Authorization");
		if(CollUtil.isEmpty(tokenList)) {
			exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
			return exchange.getResponse().setComplete();
		}
		String accessToken = tokenList.get(0);
		
		//验证jwt token
		JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(CustomConstants.JWT_SIGNING_KEY)).build();
		try {
            jwtVerifier.verify(accessToken);
        } catch (JWTVerificationException e) {
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }
		
		
		//校验用户信息是否在redis中，补足jwt token无法强制下线
		Long userId = JWT.decode(accessToken).getClaim("userId").asLong();
		boolean hasKey = redisTemplate.hasKey(CustomConstants.REDIS_USER_KEY + userId);
		
		if(!hasKey) {
		    exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
		}
		
		log.debug("accept token is {}, user info hasKey {}", accessToken, hasKey);
		
		return chain.filter(exchange);
	}



}
