package com.majiaxueyuan.boot.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.majiaxueyuan.sso.core.filter.MaJiaSSOIntercepter;

@Configuration
public class XxlSsoConfig extends WebMvcConfigurerAdapter {

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(ssoIntercepter()).addPathPatterns("/**");
	}

	@Bean
	public MaJiaSSOIntercepter ssoIntercepter() {
		return new MaJiaSSOIntercepter().setLogoutUrl("/logout").setTokenSalt("mjxy2");
	}

}