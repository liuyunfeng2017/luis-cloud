package com.cloud.luis.gateway.security.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@Data
public class CustomUserDetails implements UserDetails{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5921327473070445649L;
	
	private Long userId;
	private String username;
    @JsonIgnore
    private String password;
    private Collection<Long> roles;
    private String token;
    
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		if(!roles.isEmpty()) {
			return AuthorityUtils.commaSeparatedStringToAuthorityList(roles.toString());
		}
		return null;
	}
	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}
	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}
	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}
	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}

    

}
