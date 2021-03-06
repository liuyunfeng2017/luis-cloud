package com.cloud.luis.user;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

/**
 * 用户服务启动类
 * @author luis
 * @date 2019/12/18
 */
@ComponentScan(basePackages = {"com.cloud.luis"})
@MapperScan("com.cloud.luis.*.mapper")
@EnableDiscoveryClient
@SpringBootApplication
public class UserApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(UserApplication.class, args);
	}

}
