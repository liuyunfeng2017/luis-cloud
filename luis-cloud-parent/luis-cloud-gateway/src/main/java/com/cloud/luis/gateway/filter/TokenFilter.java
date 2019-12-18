package com.cloud.luis.gateway.filter;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ServerWebExchange;

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
	
	@Override
	public int getOrder() {
		return Ordered.LOWEST_PRECEDENCE;
	}

	@Override
	public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
		
		List<String> tokenList = exchange.getRequest().getHeaders().get("Authorization");
		if(tokenList.isEmpty()) {
			exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
			return exchange.getResponse().setComplete();
		}
		
		String accessToken = tokenList.get(0);
		boolean flag = redisTemplate.hasKey("access:" + accessToken);
		if(!flag) {
			exchange.getResponse().setStatusCode(HttpStatus.BAD_REQUEST);
			return exchange.getResponse().setComplete();
		}
		Object obj = redisTemplate.opsForValue().get("access:" + accessToken);
		if(null == obj) {
			exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
			return exchange.getResponse().setComplete();
		}
		log.debug("accept tone is {}, user info is {}", accessToken, obj.toString());
		
		return chain.filter(exchange);
	}



}
