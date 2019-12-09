package com.cloud.luis.gateway.security.service;

import java.util.Arrays;
import java.util.List;

import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.cloud.luis.gateway.security.entity.CustomUserDetails;

import cn.hutool.core.util.StrUtil;
import reactor.core.publisher.Mono;

public class UserDetailsServiceImpl implements ReactiveUserDetailsService{

	@Override
	public Mono<UserDetails> findByUsername(String username) {
		//todo 预留调用数据库根据用户名获取用户
        if(StrUtil.equals(username, "admin")){
        	CustomUserDetails user = new CustomUserDetails();
        	user.setUserId(1L);
        	user.setUsername("admin");
        	user.setPassword("123456");
        	List<Long> roles = Arrays.asList(1L,2L);
        	user.setRoles(roles);
            return Mono.just(user);
        }
        else{
            return Mono.error(new UsernameNotFoundException("User Not Found"));
        }

	}

}
