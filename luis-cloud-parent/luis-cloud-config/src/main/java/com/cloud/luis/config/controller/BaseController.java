 package com.cloud.luis.config.controller;


import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;

import com.cloud.luis.common.model.ReturnData;
import com.cloud.luis.common.properties.CustomConstants;
import com.cloud.luis.config.context.BaseContextHandler;

import cn.hutool.core.util.StrUtil;

public class BaseController {
     
     public <T> ReturnData<T> initSuccessResult(T t,String msg) {
         ReturnData<T> rd = new ReturnData<>();
         rd.setCode(CustomConstants.SUCCESS_CODE);
         rd.setMsg(StringUtils.isEmpty(msg)?"success":msg);
         rd.setResult(t);
         return rd;
     }

     public <T> ReturnData<T> initSuccessResult(T t) {
         ReturnData<T> rd = new ReturnData<>();
         rd.setCode(CustomConstants.SUCCESS_CODE);
         rd.setMsg("success");
         rd.setResult(t);
         return rd;
     }
     
     
     public <T> ReturnData<T> initSuccessResult() {
         ReturnData<T> rd = new ReturnData<>();
         rd.setCode(CustomConstants.SUCCESS_CODE);
         rd.setMsg("success");
         rd.setResult(null);
         return rd;
     }


     /**
      * @Description 初始化失败返回Object结果
      */
     public <T> ReturnData<T> initErrorResult(String desc) {
         ReturnData<T> rd = new ReturnData<>();
         rd.setCode(CustomConstants.FAIL_CODE);
         rd.setMsg(desc);
         return rd;
     } 
     
     public Long getUserId(HttpServletRequest request) {
         Long userId = null;
         String userIdStr = request.getHeader("userId");
         if(StrUtil.isNotEmpty(userIdStr)) {
             userId = Long.valueOf(userIdStr);
             BaseContextHandler.set(CustomConstants.THREAD_UER_ID_KEY, userId);
         }
         return userId;
     }
     
     
     
     

}
