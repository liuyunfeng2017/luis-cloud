package com.cloud.luis.oauth.mobile;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;

/**
 * 验证码实体类
 * @author luis
 * @date 2019/12/18
 */
public class ValidateCode implements Serializable {
    
    /** @Fields serialVersionUID: */
      	
    private static final long serialVersionUID = 8054122012266138805L;
    
    private String code;
    
    @JsonDeserialize(using = LocalDateTimeDeserializer.class) 
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime expireTime;
    
    public ValidateCode() {}

    public ValidateCode(String code, int expireIn) {
        this.code = code;
        this.expireTime = LocalDateTime.now().plusSeconds(expireIn);
    }

    public ValidateCode(String code, LocalDateTime expireTime) {
        this.code = code;
        this.expireTime = expireTime;
    }

    /**
     * 
     * @return
     
    public boolean isExpried(){
        return LocalDateTime.now().isAfter(expireTime);
    }
     */
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public LocalDateTime getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(LocalDateTime expireTime) {
        this.expireTime = expireTime;
    }
    
    
}
