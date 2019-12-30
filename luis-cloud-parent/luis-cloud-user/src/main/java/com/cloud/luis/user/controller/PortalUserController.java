package com.cloud.luis.user.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import com.cloud.luis.common.model.ReturnData;
import com.cloud.luis.user.model.PortalUser;
import com.cloud.luis.user.service.PortalUserService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 用户controller
 * @author luis
 * @since 2019-12-06
 */
@RestController
@RequestMapping("/user")
@Api("用户信息管理")
public class PortalUserController {
	
	@Autowired
	PortalUserService portalUserService;
	
	@PostMapping("/list")
	@ApiOperation(value = "查询", notes = "所有用户信息")
	public ReturnData<List<PortalUser>> getUserList(){
		List<PortalUser> userList = portalUserService.list();
		return new ReturnData<List<PortalUser>>(userList);
	}

}
