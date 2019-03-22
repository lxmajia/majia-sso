package com.majiaxueyuan.boot.config;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.majiaxueyuan.sso.core.filter.HttpSSOTokenFilter;
import com.majiaxueyuan.sso.core.util.JedisUtil;

@Configuration
public class XxlSsoConfig implements InitializingBean, DisposableBean {

	@Value("${majia.sso.redis.server:127.0.0.1}")
	private String redisServer;

	@Value("${majia.sso.redis.port:6379}")
	private int redisPort;

	@Value("${majia.sso.client.logout}")
	private String logoutUrl;

	@Value("${majia.sso.client.withOutUrl}")
	private String withOutUrl;

	@Override
	public void afterPropertiesSet() throws Exception {
		JedisUtil.init(redisServer, redisPort);
	}

	@Override
	public void destroy() throws Exception {
		JedisUtil.close();
	}

	@Bean
	public FilterRegistrationBean ssoFilterRegister() {
		// 不管怎么，都要刷新到Redis
		JedisUtil.init(redisServer, redisPort);
		FilterRegistrationBean ssoFilter = new FilterRegistrationBean();
		ssoFilter.setName("majia-sso");
		ssoFilter.setOrder(1);
		ssoFilter.addUrlPatterns("/*");
		ssoFilter.setFilter(new HttpSSOTokenFilter().setLogoutUrl(logoutUrl).setWithOutUrl(withOutUrl));
		return ssoFilter;
	}

}