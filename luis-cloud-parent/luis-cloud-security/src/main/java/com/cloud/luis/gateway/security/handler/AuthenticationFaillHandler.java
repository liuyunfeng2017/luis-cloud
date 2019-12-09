package com.cloud.luis.gateway.security.handler;

import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.server.WebFilterExchange;
import org.springframework.security.web.server.authentication.ServerAuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import com.cloud.luis.common.model.ReturnData;
import com.fasterxml.jackson.databind.ObjectMapper;

import reactor.core.publisher.Mono;

@Component
public class AuthenticationFaillHandler implements ServerAuthenticationFailureHandler {

	@Override
	public Mono<Void> onAuthenticationFailure(WebFilterExchange webFilterExchange, AuthenticationException e) {
		ServerWebExchange exchange = webFilterExchange.getExchange();
		ServerHttpResponse response = exchange.getResponse();
		// 设置headers
		HttpHeaders httpHeaders = response.getHeaders();
		httpHeaders.add("Content-Type", "application/json; charset=UTF-8");
		httpHeaders.add("Cache-Control", "no-store, no-cache, must-revalidate, max-age=0");
		// 设置body
		ReturnData<String> rd = new ReturnData<>(e);
		byte[] dataBytes = {};
		try {
			ObjectMapper mapper = new ObjectMapper();
			dataBytes = mapper.writeValueAsBytes(rd);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		DataBuffer bodyDataBuffer = response.bufferFactory().wrap(dataBytes);
		return response.writeWith(Mono.just(bodyDataBuffer));
	}

}
