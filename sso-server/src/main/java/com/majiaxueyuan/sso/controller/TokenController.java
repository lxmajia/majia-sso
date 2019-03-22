package com.majiaxueyuan.sso.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.majiaxueyuan.sso.core.constans.Result;
import com.majiaxueyuan.sso.core.helper.TokenLoginHelper;

@RestController
public class TokenController {
	@RequestMapping("/login")
	public String login(Long id, String username, String password) {

		if (username.equals("admin") && password.equals("123456")) {
			// 表示登录成功
			Result loginSuccess = TokenLoginHelper.loginSuccess(id, username, password);
			String token = loginSuccess.getData().toString();
			System.out.println(token);
			return "token:" + token;
		}
		return "failed";
	}

	@RequestMapping("/hello")
	public String hello() {
		return "hello";
	}
}
