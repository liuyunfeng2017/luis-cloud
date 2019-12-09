package com.cloud.luis.user.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import com.cloud.luis.common.model.ReturnData;
import com.cloud.luis.user.model.PortalUser;
import com.cloud.luis.user.service.PortalUserService;

/**
 * <p>
 * 用户表 前端控制器
 * </p>
 *
 * @author luis
 * @since 2019-12-06
 */
@RestController
@RequestMapping("/user")
public class PortalUserController {
	
	@Autowired
	PortalUserService portalUserService;
	
	@PostMapping("/list")
	public ReturnData<List<PortalUser>> getUserList(){
		List<PortalUser> userList = portalUserService.list();
		return new ReturnData<List<PortalUser>>(userList);
	}

}
