package com.cloud.luis.config.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Component;

import com.cloud.luis.common.properties.CustomConstants;
import com.cloud.luis.config.context.BaseContextHandler;

import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@WebFilter(value = "/**")
public class SecurityFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
        throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest)request;
        Long userId = null;
        String UserIdStr = httpRequest.getHeader(CustomConstants.GATEWAY_HEADER_USER_KEY);
        if (StrUtil.isNotEmpty(UserIdStr)) {
            userId = Long.valueOf(UserIdStr);
            BaseContextHandler.set(CustomConstants.THREAD_UER_ID_KEY, userId);
            log.debug("SecurityFilter accpet user id {}", userId);
        }
        chain.doFilter(httpRequest, response);
    }

}
