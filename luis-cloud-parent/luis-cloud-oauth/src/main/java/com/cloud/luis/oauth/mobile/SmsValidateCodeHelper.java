package com.cloud.luis.oauth.mobile;

import java.util.concurrent.TimeUnit;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import com.cloud.luis.common.exception.CustomException;

import lombok.extern.slf4j.Slf4j;

/**
 * 验证码生成及校验工具类
 * @author luis
 * @date 2019/12/18
 */
@Slf4j
@Component
public class SmsValidateCodeHelper {
    
    @Autowired
    private RedisTemplate<Object,Object> redisTemplate;
    
    public void send(HttpServletRequest request) {
        String mobile = request.getParameter("mobile");
        String codeKey = buildKey(request);
        ValidateCode validateCode = generate(codeKey, request);
        redisTemplate.opsForValue().set(codeKey, validateCode, 3, TimeUnit.MINUTES);
        log.info("向手机{}发送短信验证码{}",mobile, validateCode.getCode());
    }
    
    public ValidateCode generate(String codeKey, HttpServletRequest request) {
        String code = RandomStringUtils.randomNumeric(6);
        log.debug("SmsValidateCodeHelp generate code to redis key {}", codeKey);
        return new ValidateCode(code, 180);
    }
    
    public ValidateCode get(HttpServletRequest request) {
        String codeKey = buildKey(request);
        log.debug("SmsValidateCodeHelp get code from redis key {}", codeKey);
        Object value = redisTemplate.opsForValue().get(codeKey);
        if(value==null){
            return null;
        }
        return (ValidateCode) value;
    }
    
    public void remove(HttpServletRequest request) {
        redisTemplate.delete(buildKey(request));
    }
    
    public void validate(HttpServletRequest request) {
        String codeInRequest;
        codeInRequest = request.getParameter("code");
        if (StringUtils.isBlank(codeInRequest)) {
            throw new CustomException("验证码的值不能为空");
        }
        ValidateCode codeInRedis = get(request);
        if (codeInRedis == null) {
            throw new CustomException("验证码不存在");
        }
        if (codeInRedis.isExpried()) {
            remove(request);
            throw new CustomException("验证码已过期");
        }
        if (!StringUtils.equals(codeInRedis.getCode(), codeInRequest)) {
            throw new CustomException("验证码不匹配");
        }
        remove(request);
    }
    
    private String buildKey(HttpServletRequest request){
        String mobile = request.getParameter("mobile");
        if(StringUtils.isBlank(mobile)){
            throw new CustomException("请在请求参数中携带mobile参数");
        }
        return "code:mobile:" + mobile;
    }

}
