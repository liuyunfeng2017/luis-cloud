package com.cloud.luis.gateway.filter;


import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import com.cloud.luis.common.properties.CustomConstants;

import reactor.core.publisher.Mono;

/**
 * gateway请求body读取一次就会清空，把请求body缓存一下方便多次读取
 * 
 * @author luis
 * @date 2020/01/02
 */

@Component
public class RequestFilter implements GlobalFilter, Ordered {

    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        return DataBufferUtils.join(exchange.getRequest().getBody()).map(dataBuffer -> {
            byte[] bytes = new byte[dataBuffer.readableByteCount()];
            dataBuffer.read(bytes);
            DataBufferUtils.release(dataBuffer);
            return bytes;
        }).defaultIfEmpty(new byte[0])
            .doOnNext(bytes -> exchange.getAttributes().put(CustomConstants.CACHE_REQ_BODY_KEY, bytes))
            .then(chain.filter(exchange));
    }

}
