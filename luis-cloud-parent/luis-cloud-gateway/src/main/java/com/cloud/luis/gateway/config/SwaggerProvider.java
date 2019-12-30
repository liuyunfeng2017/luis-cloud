package com.cloud.luis.gateway.config;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import cn.hutool.core.util.StrUtil;
import lombok.AllArgsConstructor;
import springfox.documentation.swagger.web.SwaggerResource;
import springfox.documentation.swagger.web.SwaggerResourcesProvider;

/**
 * 获取Api-doc SwaggerResources
 * 
 * @author luis
 * @date 2020/12/30
 */

@Component
@Primary
@AllArgsConstructor
public class SwaggerProvider implements SwaggerResourcesProvider {

    public static final String API_URI = "/v2/api-docs";
    
    private final RouteLocator routeLocator;
    

    @Override
    public List<SwaggerResource> get() {
        List<SwaggerResource> resources = new ArrayList<>();
        
        routeLocator.getRoutes().filter(route -> StrUtil.isNotEmpty(route.getPredicate().toString()))
                                .subscribe(route -> resources.add(swaggerResource(route.getId(), route.getPredicate().toString())));
        
        return resources;
    }

    
    private SwaggerResource swaggerResource(String name, String predicate) {
        
        String regex = "\\[(.*?)]";
        Pattern pattern = Pattern.compile(regex);
        
        SwaggerResource swaggerResource = new SwaggerResource();
        swaggerResource.setName(name);
        Matcher matcher = pattern.matcher(predicate);
        while (matcher.find()) {
            String uriStr = matcher.group(1);
            String[] strArrays = uriStr.split(",");
            swaggerResource.setUrl(strArrays[0].trim().replace("/**", API_URI));
        }
        swaggerResource.setSwaggerVersion("2.9.2");
        return swaggerResource;
    }


}
