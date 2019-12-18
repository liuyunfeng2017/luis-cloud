package com.cloud.luis.oauth.openid;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

/**
 * openId今牌处理类
 * @author luis
 * @date 2019/12/18
 */
public class OpenIdAuthenticationToken extends AbstractAuthenticationToken {

    
    /** @Fields serialVersionUID: */
      	
    private static final long serialVersionUID = 607081763187659042L;
    private final Object principal;  //放的openId

    public OpenIdAuthenticationToken(String openId,String providerId) {
        super(null);
        this.principal = openId;
        setAuthenticated(false);
    }


    public OpenIdAuthenticationToken(Object principal, Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.principal = principal;
        super.setAuthenticated(true);
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return this.principal;
    }

    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
        if (isAuthenticated) {
            throw new IllegalArgumentException("Cannot set this token to trusted - use constructor which takes a GrantedAuthority list instead");
        }
        super.setAuthenticated(false);
    }

    @Override
    public void eraseCredentials() {
        super.eraseCredentials();
    }
}
