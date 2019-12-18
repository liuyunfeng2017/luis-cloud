package com.cloud.luis.oauth.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;

import com.cloud.luis.oauth.mobile.SmsCodeAuthenticationSecurityConfig;
import com.cloud.luis.oauth.mobile.ValidateCodeSecurityConfig;
import com.cloud.luis.oauth.openid.OpenIdAuthenticationSecurityConfig;
/**
 * 资源服务器配置类
 * @author luis
 * @date 2019/12/18
 */
@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {
  
	@Autowired
	private SmsCodeAuthenticationSecurityConfig smsCodeAuthenticationSecurityConfig;

	@Autowired
	private ValidateCodeSecurityConfig validateCodeSecurityConfig;
	
	@Autowired
	OpenIdAuthenticationSecurityConfig openIdAuthenticationSecurityConfig;
	
	private static final String[] EXCLUDE_AUTH_PAGES = { "/oauth/token", "/health", "/api/socket/**" };

	@Override
	public void configure(HttpSecurity http) throws Exception {
		ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry registry = http.authorizeRequests();
		registry.and().formLogin()
				.and()
				.headers().frameOptions().disable().and().exceptionHandling()
				.and()
				.apply(smsCodeAuthenticationSecurityConfig)
				.and()
				.apply(validateCodeSecurityConfig)
				.and()
				.apply(openIdAuthenticationSecurityConfig)
				.and()
				.csrf()
				.disable();
		registry.antMatchers(EXCLUDE_AUTH_PAGES).permitAll();
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
  
  

}
