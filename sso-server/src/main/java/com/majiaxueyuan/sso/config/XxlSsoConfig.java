package com.majiaxueyuan.sso.config;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import com.majiaxueyuan.sso.core.util.JedisUtil;

@Configuration
public class XxlSsoConfig implements InitializingBean, DisposableBean {

	@Value("${majia.sso.redis.server:127.0.0.1}")
	private String redisServer;

	@Value("${majia.sso.redis.port:6379}")
	private int redisPort;

	@Override
	public void afterPropertiesSet() throws Exception {
		JedisUtil.init(redisServer, redisPort);
	}

	@Override
	public void destroy() throws Exception {
		JedisUtil.close();
	}

}