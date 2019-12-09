package com.cloud.luis.gateway.security.handler;

import com.alibaba.fastjson.JSONObject;
import com.cloud.luis.common.model.ReturnData;
import com.cloud.luis.gateway.security.entity.CustomUserDetails;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.server.WebFilterExchange;
import org.springframework.security.web.server.authentication.WebFilterChainServerAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Base64;

@Component
public class AuthenticationSuccessHandler extends WebFilterChainServerAuthenticationSuccessHandler {

	@Override
	public Mono<Void> onAuthenticationSuccess(WebFilterExchange webFilterExchange, Authentication authentication) {
		ServerWebExchange exchange = webFilterExchange.getExchange();
		ServerHttpResponse response = exchange.getResponse();
		// 设置headers
		HttpHeaders httpHeaders = response.getHeaders();
		httpHeaders.add("Content-Type", "application/json; charset=UTF-8");
		httpHeaders.add("Cache-Control", "no-store, no-cache, must-revalidate, max-age=0");
		// 设置body
		ReturnData<CustomUserDetails> rd;
		byte[] dataBytes = {};
		ObjectMapper mapper = new ObjectMapper();
		try {
			User user = (User) authentication.getPrincipal();
			CustomUserDetails userDetails = buildUser(user);
			byte[] authorization = (userDetails.getUsername() + ":" + userDetails.getPassword()).getBytes();
			String token = Base64.getEncoder().encodeToString(authorization);
			httpHeaders.add(HttpHeaders.AUTHORIZATION, token);
			rd = new ReturnData<>(userDetails);
			dataBytes = mapper.writeValueAsBytes(rd);
		} catch (Exception ex) {
			ex.printStackTrace();
			JSONObject result = new JSONObject();
			result.put("code", 0);
			result.put("msg", ex.getMessage());
			dataBytes = result.toString().getBytes();
		}
		DataBuffer bodyDataBuffer = response.bufferFactory().wrap(dataBytes);
		return response.writeWith(Mono.just(bodyDataBuffer));
	}

	private CustomUserDetails buildUser(User user) {
		CustomUserDetails userDetails = new CustomUserDetails();
		userDetails.setUserId(1L);
		userDetails.setUsername(user.getUsername());
		userDetails.setPassword(user.getPassword().substring(user.getPassword().lastIndexOf("}") + 1, user.getPassword().length()));
		return userDetails;
	}

}
