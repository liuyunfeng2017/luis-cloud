package com.cloud.luis.oauth.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
/**
 * security安全认证配置类
 * @author luis
 * @date 2019/12/18
 */
@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    

	@Override
	public void configure(HttpSecurity http) throws Exception {
        http.csrf()
            .disable()
            .authorizeRequests()
            .antMatchers("/oauth/**", "/login/**", "/logout/**")
            .permitAll()
            .anyRequest()
            .authenticated()
            .and()
            .formLogin()
            .permitAll();
	}
	

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }


	

}
