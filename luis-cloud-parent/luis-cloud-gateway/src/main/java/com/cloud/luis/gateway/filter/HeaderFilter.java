 package com.cloud.luis.gateway.filter;

import java.util.List;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import com.auth0.jwt.JWT;
import com.cloud.luis.common.properties.CustomConstants;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import reactor.core.publisher.Mono;

/**
 * 把token中用户ID往后端微服务传递
 * @author luis
 * @date 2020/01/02
 */
@Component
public class HeaderFilter implements GlobalFilter, Ordered {

    @Override
    public int getOrder() {
         return Ordered.LOWEST_PRECEDENCE - 100;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest().mutate().headers(httpHeaders -> {
            List<String> tokenList = httpHeaders.get("Authorization");
            String accessToken = null;
            Long userId = null;
            if(!CollUtil.isEmpty(tokenList)) {
                accessToken = tokenList.get(0);
            }
            //从token中取用户ID
            if(StrUtil.isNotEmpty(accessToken)) {
                userId = JWT.decode(accessToken).getClaim("userId").asLong();
            }
            httpHeaders.add(CustomConstants.GATEWAY_HEADER_USER_KEY, userId+"");
        }).build();
        
        ServerWebExchange build = exchange.mutate().request(request).build();
        
        return chain.filter(build);
    }

}
