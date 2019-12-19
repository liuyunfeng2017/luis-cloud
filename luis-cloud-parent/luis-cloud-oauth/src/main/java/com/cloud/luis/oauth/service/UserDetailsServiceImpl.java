package com.cloud.luis.oauth.service;

import java.util.Collection;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.cloud.luis.common.model.UserInfo;
import com.cloud.luis.common.properties.CustomConstants;
import com.cloud.luis.oauth.model.SecurityUserDetail;

import cn.hutool.core.util.StrUtil;

/**
 * 自定义用户认证实现类
 * @author luis
 * @date 2019/12/18
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	RedisTemplate<String, Object> redisTemplate;
	
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    	SecurityUserDetail user = null;
        if(StrUtil.equals(username, "admin") || StrUtil.equals(username, "18938855273")) {
        	String password = passwordEncoder.encode("123456");
        	Collection<? extends GrantedAuthority> authorities = AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_BASE");
        	user = new SecurityUserDetail(1L, username, password, true, true, true, true, authorities);
        	
        	//把用户信息放到redis中，方便其它微服务获取用户信息
        	UserInfo userInfo = new UserInfo();
        	userInfo.setUserId(user.getUserId());
        	redisTemplate.opsForValue().set(CustomConstants.REDIS_USER_KEY + user.getUserId(), userInfo, 2, TimeUnit.HOURS);
        }
        return user;
    }

}
