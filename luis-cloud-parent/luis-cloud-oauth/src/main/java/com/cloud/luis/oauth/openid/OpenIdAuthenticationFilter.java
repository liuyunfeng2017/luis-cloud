package com.cloud.luis.oauth.openid;

import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class OpenIdAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    protected OpenIdAuthenticationFilter() {
        super(new AntPathRequestMatcher("/social/login", "POST"));
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws AuthenticationException, IOException, ServletException {
        if(!httpServletRequest.getMethod().equals("POST")){
            throw new AuthenticationServiceException("Authentication method not supported:"+httpServletRequest.getMethod());
        }
        String openid = obtainOpenId(httpServletRequest);
        String providerId = obtainProviderId(httpServletRequest);

        
        if(providerId==null){
            providerId = "";
        }

        openid = openid.trim();
        providerId = providerId.trim();

        OpenIdAuthenticationToken authRequest = new OpenIdAuthenticationToken(openid,providerId);

        setDetails(httpServletRequest,authRequest);
        //交给AuthenticationManager校验
        return this.getAuthenticationManager().authenticate(authRequest);
    }

    /**
     * 获取openId
     */
    protected String obtainOpenId(HttpServletRequest request){
    	String openId = request.getParameter("openId");
    	if(openId.isEmpty()){
    		openId = "";
        }
        return openId;
    }

    /**
     * 获取loginType
     */
    protected String obtainProviderId(HttpServletRequest request){
    	String loginType = request.getParameter("loginType");
    	if(loginType.isEmpty()){
    		loginType = "";
        }
        return loginType;
    }

    protected void setDetails(HttpServletRequest request, OpenIdAuthenticationToken authRequest) {
        authRequest.setDetails(authenticationDetailsSource.buildDetails(request));
    }

    
}
