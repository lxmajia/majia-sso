package com.majiaxueyuan.sso.core.filter;

import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.majiaxueyuan.sso.core.annotation.NoToken;
import com.majiaxueyuan.sso.core.constans.Result;
import com.majiaxueyuan.sso.core.constans.SysCfg;
import com.majiaxueyuan.sso.core.entity.SSOUser;
import com.majiaxueyuan.sso.core.util.JwtTokenUtils;

public class MaJiaSSOIntercepter implements HandlerInterceptor {

	private String logoutUrl;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object object) throws Exception {
		String servletPath = request.getServletPath();
		System.out.println("######Intercepter : " + servletPath);
		// 判断是不是不需要验证的URL

		// 判断是不是退出
		if (servletPath.equals(logoutUrl)) {
			response.getWriter().println("{\"code\":" + Result.SUCCESS_CODE + ", \"msg\":\"" + "退出成功" + "\"}");
			return true;
		}

		// 如果不是映射到方法直接通过
		if (!(object instanceof HandlerMethod)) {
			return true;
		}

		HandlerMethod handlerMethod = (HandlerMethod) object;
		Method method = handlerMethod.getMethod();

		// 检查是否有passtoken注释，有则跳过认证

		if (method.isAnnotationPresent(NoToken.class)) {
			NoToken passToken = method.getAnnotation(NoToken.class);
			if (passToken.notNeedToken()) {
				return true;
			}
		} else {
			String token = request.getHeader("Authorization");
			// Token不存在
			if (token == null) {
				response.setStatus(HttpServletResponse.SC_OK);
				response.setContentType("application/json;charset=UTF-8");
				response.getWriter().println("{\"code\":" + Result.NO_AUTHORIZATION + ", \"msg\":\"" + "未登录" + "\"}");
				return false;
			}
			// 验证Token是否有效
			try {
				Boolean checkTokenInLaw = JwtTokenUtils.checkTokenInLaw(token);
				if (!checkTokenInLaw) {
					response.setStatus(HttpServletResponse.SC_OK);
					response.setContentType("application/json;charset=UTF-8");
					response.getWriter()
							.println("{\"code\":" + Result.NO_AUTHORIZATION + ", \"msg\":\"" + "非法请求，请立即停止操作" + "\"}");
					return false;
				}
				SSOUser ssoUser = JwtTokenUtils.getTokenSSOUser(token);
				request.setAttribute("ssoUser", ssoUser);
				return true;
			} catch (JWTVerificationException e) {
				response.setStatus(HttpServletResponse.SC_OK);
				response.setContentType("application/json;charset=UTF-8");
				response.getWriter()
						.println("{\"code\":" + Result.NO_AUTHORIZATION + ", \"msg\":\"" + "非法请求，请立即停止操作" + "\"}");
				return false;
			}
		}
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o,
			ModelAndView modelAndView) throws Exception {
	}

	@Override
	public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
			Object o, Exception e) throws Exception {
	}

	// ##########################################
	public String getLogoutUrl() {
		return logoutUrl;
	}

	public MaJiaSSOIntercepter setLogoutUrl(String logoutUrl) {
		this.logoutUrl = logoutUrl;
		return this;
	}

	public MaJiaSSOIntercepter setTokenSalt(String salt) {
		SysCfg.TOKEN_SALT = salt;
		return this;
	}
}