package com.cloud.luis.oauth.model;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import lombok.Getter;

public class SecurityUserDetail extends User {

	  private static final long serialVersionUID = -5264301204847053336L;
	  /**
	   * 用户ID
	   */
	  @Getter
	  private Long userId;

	  public SecurityUserDetail(Long userId, String username, String password, boolean enabled, boolean accountNonExpired,
	      boolean credentialsNonExpired, boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities) {
	    super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
	    this.userId = userId;
	  }

}
