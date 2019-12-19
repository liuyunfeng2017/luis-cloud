package com.cloud.luis.common.utils;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import cn.hutool.core.collection.CollUtil;

public final class PermitAllUrl {

    /**
     * 监控中心和swagger需要访问的url
     */
    private static final String[] ENDPOINTS = {"/actuator/health", "/actuator/env", "/actuator/metrics/**", "/actuator/trace", "/actuator/dump",
            "/actuator/jolokia", "/actuator/info", "/actuator/logfile", "/actuator/refresh", "/actuator/flyway", "/actuator/liquibase",
            "/actuator/heapdump", "/actuator/loggers", "/actuator/auditevents", "/actuator/env/PID", "/actuator/jolokia/**",
            "/v2/api-docs/**", "/swagger-ui.html", "/swagger-resources/**", "/webjars/**"};

    public static String[] permitAllUrl(List<String> list) {
        if(CollUtil.isEmpty(list)) {
            return ENDPOINTS;
        }
        Set<String> set = new HashSet<>();
        Collections.addAll(set, ENDPOINTS);
        list.stream().forEach(ignoreurl -> Collections.addAll(set, ignoreurl));
        return set.toArray(new String[set.size()]);
    }

}
