package com.cloud.luis.gateway.filter;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.http.server.reactive.ServerHttpRequestDecorator;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import com.cloud.luis.common.properties.CustomConstants;

import cn.hutool.core.util.ObjectUtil;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * 返回过滤处理
 * @author luis
 * @date 2020/01/02
 */

@Component
public class ResponseFilter implements GlobalFilter, Ordered {

    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE + 10;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        Object requestBody = exchange.getAttributeOrDefault(CustomConstants.CACHE_REQ_BODY_KEY, null);
        if (ObjectUtil.isNotEmpty(requestBody)) {
            byte[] body = (byte[])requestBody;
            DataBufferFactory dataBufferFactory = exchange.getResponse().bufferFactory();
            ServerHttpRequestDecorator decorator = new ServerHttpRequestDecorator(exchange.getRequest()) {
                @Override
                public Flux<DataBuffer> getBody() {
                    if (body.length > 0) {
                        return Flux.just(dataBufferFactory.wrap(body));
                    }
                    return Flux.empty();
                }
            };
            return chain.filter(exchange.mutate().request(decorator).build());
        }
        return chain.filter(exchange);
    }

}
