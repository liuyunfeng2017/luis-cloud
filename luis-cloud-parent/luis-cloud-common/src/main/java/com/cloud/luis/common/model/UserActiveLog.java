 package com.cloud.luis.common.model;

import java.io.Serializable;
import java.time.LocalDateTime;

import lombok.Data;

/**
 * 用户操作日志实体类
 * @author luis
 * @date 2020/01/02
 */

@Data
public class UserActiveLog implements Serializable{
    
    /**
     *
     */
    private static final long serialVersionUID = 5629551347642469549L;
    
    private Long userId;
    
    private LocalDateTime requestTime;
    
    private LocalDateTime responseTime;
    
    private Long elapsedTime;
    
    private String requestIp;
    
    private String method;
    
    private String requestUri;
    
    private String requestBodyJson;
    
    

}
