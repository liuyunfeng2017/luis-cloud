package com.cloud.luis.common.model;

import java.io.Serializable;
import java.util.Collection;


import lombok.Data;

@Data
public class UserInfo implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5757964393548163491L;
	
	private Long userId;
	private String username;
	private String imageUrl;
    private Collection<Long> roles;
    private Collection<String> permissions;
    private String token;

}
