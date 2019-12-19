package com.cloud.luis.common.utils;

import org.jasypt.util.text.BasicTextEncryptor;

/**
 * Jasypt加解密示例
 * @author luis
 * @date 2019/12/18
 */
public class JasyptUtil {
	
    /**
     * 
     * @param args
     
	public static void main(String[] args) {
		BasicTextEncryptor textEncryptor = new BasicTextEncryptor();
        //加密所需的salt(盐)
        textEncryptor.setPassword("bl123456");
        //要加密的数据（数据库的用户名或密码）
        String username = textEncryptor.encrypt("root");
        String password = textEncryptor.encrypt("poly2017");
        System.out.println("username:"+username);
        System.out.println("password:"+password);
	}
*/
}
