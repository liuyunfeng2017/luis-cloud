package com.cloud.luis.oauth.mobile;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 验证码实体类
 * @author luis
 * @date 2019/12/18
 */
public class ValidateCode implements Serializable {
    
    /** @Fields serialVersionUID: */
      	
    private static final long serialVersionUID = 8054122012266138805L;
    
    private String code;
    private LocalDateTime expireTime;

    public ValidateCode(String code, int expireIn) {
        this.code = code;
        this.expireTime = LocalDateTime.now().plusSeconds(expireIn);
    }

    public ValidateCode(String code, LocalDateTime expireTime) {
        this.code = code;
        this.expireTime = expireTime;
    }

    public boolean isExpried(){
        return LocalDateTime.now().isAfter(expireTime);
    }

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
