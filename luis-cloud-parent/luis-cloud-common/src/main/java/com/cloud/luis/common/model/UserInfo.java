package com.cloud.luis.common.model;

import java.io.Serializable;
import java.util.Collection;


import lombok.Data;

/**
 *     用户信息对象类
 * @author luis
 * @date 2019/12/18
 */
@Data
public class UserInfo implements Serializable{
	

	private static final long serialVersionUID = 5757964393548163491L;
	
	private Long userId;
	private String username;
	private String imageUrl;
    private Collection<Long> roles;
    private Collection<String> permissions;
    private String token;
    
    @Override
    public String toString() {
        return "UserInfo [userId=" + userId + ", username=" + username + ", imageUrl=" + imageUrl + ", roles=" + roles
            + ", permissions=" + permissions + ", token=" + token + "]";
    }
    
    

}
