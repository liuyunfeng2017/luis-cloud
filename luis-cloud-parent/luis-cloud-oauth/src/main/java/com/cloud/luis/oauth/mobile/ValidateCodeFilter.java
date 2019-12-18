package com.cloud.luis.oauth.mobile;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import cn.hutool.core.util.StrUtil;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * 验证码校验过滤器
 * @author luis
 * @date 2019/12/18
 */
@Component("validateCodeFilter")
public class ValidateCodeFilter extends OncePerRequestFilter implements InitializingBean {

    
    private List<String> codeurls = new ArrayList<>();

    private AntPathMatcher antPathMatcher = new AntPathMatcher();
    
    @Autowired
    SmsValidateCodeHelper smsValidateCodeHelper;
    
    @Override
    public void afterPropertiesSet() throws ServletException {
        super.afterPropertiesSet();
        codeurls.add("/login/mobile");
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        AtomicBoolean checkCode = new AtomicBoolean(false);
        codeurls.stream().filter(url -> StrUtil.isNotEmpty(url)
                && antPathMatcher.match(url, request.getRequestURI()))
                .findFirst().ifPresent(url -> checkCode.set(true));
        if(checkCode.get()) {
        	smsValidateCodeHelper.validate(request);
        }
        chain.doFilter(request, response);

    }

}
