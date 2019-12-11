package com.cloud.luis.oauth.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cloud.luis.common.model.ReturnData;
import com.cloud.luis.oauth.mobile.SmsValidateCodeHelper;

@RestController
@RequestMapping("/oauth")
public class SecurityController {

	@Autowired
	private SmsValidateCodeHelper smsValidateCodeHelper;

	@GetMapping("/me")
	public Object postuser() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		return authentication.getPrincipal();
	}

	@GetMapping("/smscode")
	public ReturnData<String> createCode(HttpServletRequest request) {
		smsValidateCodeHelper.send(request);
		return new ReturnData<>("");
	}

}
