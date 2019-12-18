package com.cloud.luis.oauth.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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
	
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    	SecurityUserDetail user = null;
        if(StrUtil.equals(username, "admin") || StrUtil.equals(username, "18938855273")) {
        	String password = passwordEncoder.encode("123456");
        	Collection<? extends GrantedAuthority> authorities = AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_BASE");
        	user = new SecurityUserDetail(1L, username, password, true, true, true, true, authorities);

        }
        return user;
    }

}
