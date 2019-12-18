package com.cloud.luis.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import com.cloud.luis.gateway.filter.TokenFilter;
/**
 * 网关服务启动类
 * @author luis
 * @date 2019/12/18
 */
@ComponentScan(basePackages = { "com.cloud.luis" })
@SpringBootApplication
@EnableDiscoveryClient
public class GateWayApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(GateWayApplication.class, args);
	}
	
	@Bean
	public TokenFilter tokenFilter() {
		TokenFilter tokenFilter = new TokenFilter();
		return tokenFilter;
	}

}
