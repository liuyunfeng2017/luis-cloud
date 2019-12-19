package com.cloud.luis.oauth.config;


import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

import com.cloud.luis.common.properties.CustomConstants;
import com.cloud.luis.oauth.supper.CustomerAccessTokenConverter;


/**
 * 认证服务器配置类
 * @author luis
 * @date 2019/12/18
 */
@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {
	
	@Autowired
    private AuthenticationManager authenticationManager;
    
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Override
	public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
		security.allowFormAuthenticationForClients()
			.tokenKeyAccess("isAuthenticated()")
			.checkTokenAccess("permitAll()");
	}

	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
		clients.inMemory().withClient("luisClientId")
              .secret(new BCryptPasswordEncoder().encode("luisSecret"))
              .accessTokenValiditySeconds(7200)
              .refreshTokenValiditySeconds(60 * 60 * 24 * 15)
              .authorizedGrantTypes("refresh_token", "password", "authorization_code")
              .scopes("custom");
	}

	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
	    TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();
        tokenEnhancerChain.setTokenEnhancers(Arrays.asList(tokenEnhancer(), jwtAccessTokenConverter()));
        endpoints.allowedTokenEndpointRequestMethods(HttpMethod.GET, HttpMethod.POST)
            .tokenEnhancer(tokenEnhancerChain)
            .authenticationManager(authenticationManager)
			.tokenStore(jwtTokenStore())
			.accessTokenConverter(jwtAccessTokenConverter())
			.userDetailsService(userDetailsService)
			.reuseRefreshTokens(true);
	}
	
	/**
	 * redis Token store.
	 * @return TokenStore
	 
	@Bean
	public TokenStore rredisTokenStore() {
		RedisTokenStore tokenStore = new RedisTokenStore(redisConnectionFactory);
		return tokenStore;
	}
	*/
	/**
	 * jwt Token Store
	 * @return TokenStore
	 */
	@Bean
    public TokenStore jwtTokenStore() {
        return new JwtTokenStore(jwtAccessTokenConverter());
    }

    @Bean
    public JwtAccessTokenConverter jwtAccessTokenConverter() {
        JwtAccessTokenConverter accessTokenConverter = new JwtAccessTokenConverter();
        accessTokenConverter.setSigningKey(CustomConstants.JWT_SIGNING_KEY);//配置JWT使用的秘钥
        accessTokenConverter.setAccessTokenConverter(new CustomerAccessTokenConverter()); //令牌内容增强
        return accessTokenConverter;
    }
    
    /**
                 *  返回报文增强
     * @return
     */
    @Bean
    public TokenEnhancer tokenEnhancer() {
        return (accessToken, authentication) -> {
            final Map<String, Object> additionalInfo = new HashMap<>(2);
            additionalInfo.put("license", "luis owner");
            ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(additionalInfo);
            return accessToken;
        };
    }
	
	
	
	

}
