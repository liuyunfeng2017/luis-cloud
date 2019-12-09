package com.cloud.luis.gateway.security.handler;

import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.server.authentication.HttpBasicServerAuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.web.server.ServerWebExchange;

import com.alibaba.fastjson.JSONObject;

import reactor.core.publisher.Mono;

@Component
public class CustomHttpBasicServerAuthenticationEntryPoint extends HttpBasicServerAuthenticationEntryPoint {

	private static String WWW_AUTHENTICATE_FORMAT = "Basic realm=\"%s\"";
	private String headerValue = createHeaderValue("Realm");

	public CustomHttpBasicServerAuthenticationEntryPoint() {
	}

	public void setRealm(String realm) {
		this.headerValue = createHeaderValue(realm);
	}

	private static String createHeaderValue(String realm) {
		Assert.notNull(realm, "realm cannot be null");
		return String.format(WWW_AUTHENTICATE_FORMAT, new Object[] { realm });
	}

	@Override
	public Mono<Void> commence(ServerWebExchange exchange, AuthenticationException e) {
		ServerHttpResponse response = exchange.getResponse();
		response.setStatusCode(HttpStatus.UNAUTHORIZED);
		response.getHeaders().add("Content-Type", "application/json; charset=UTF-8");
		response.getHeaders().set(HttpHeaders.AUTHORIZATION, this.headerValue);
		JSONObject result = new JSONObject();
		result.put("code", 0);
		result.put("msg", e.getMessage());
		byte[] dataBytes = result.toString().getBytes();
		DataBuffer bodyDataBuffer = response.bufferFactory().wrap(dataBytes);
		return response.writeWith(Mono.just(bodyDataBuffer));
	}

}
