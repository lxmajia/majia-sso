package com.majiaxueyuan.boot.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.majiaxueyuan.sso.core.entity.SSOUser;

@RestController
public class ClientController {
	@RequestMapping("/hello")
	public String hello(HttpServletRequest req) {
		SSOUser user = (SSOUser) req.getAttribute("authInfo");
		System.out.println(user.toString());
		return "CLIENT HELLO";
	}

	@RequestMapping("/wei")
	public String wei() {
		return "CLIENT WEI";
	}

	@RequestMapping("/user/say")
	public String usersay() {
		return "CLIENT usersay";
	}
}
