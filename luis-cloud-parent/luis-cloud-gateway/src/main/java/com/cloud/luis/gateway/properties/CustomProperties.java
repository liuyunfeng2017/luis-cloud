package com.cloud.luis.gateway.properties;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

/**
 * 通用可配类
 * @author luis
 * @date 2019/12/20
 */
@Data
@Configuration()
@ConfigurationProperties(prefix = "custom.exclude.urls")
public class CustomProperties {

    private List<String> excludeUrls = new ArrayList<String>();
    
    

}
