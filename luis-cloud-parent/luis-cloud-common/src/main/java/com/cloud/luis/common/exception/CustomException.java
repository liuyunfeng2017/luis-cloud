package com.cloud.luis.common.exception;

/**
 * 自定义异常封装类
 * @author luis
 * @date 2019/12/18
 */
public class CustomException extends RuntimeException {

    
    /** @Fields serialVersionUID: */
      	
    private static final long serialVersionUID = -19125853366874667L;
    
    /**
     * @param message
     */
    public CustomException(String message) {
        super(message);
    }

}
