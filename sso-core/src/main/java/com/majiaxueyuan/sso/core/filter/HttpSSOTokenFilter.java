package com.majiaxueyuan.sso.core.filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.majiaxueyuan.sso.core.constans.Result;
import com.majiaxueyuan.sso.core.constans.SysCfg;
import com.majiaxueyuan.sso.core.entity.SSOUser;
import com.majiaxueyuan.sso.core.store.TokenLoginStore;

// 这个是过滤器，去判断当前用户是否登录，当前请求是否退出
public class HttpSSOTokenFilter extends HttpServlet implements Filter {

	private static final long serialVersionUID = 1L;

	private String logoutUrl;
	private static List<String> noUrlList = new ArrayList<>();
	private String withOutUrl;

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		logoutUrl = "/logout";
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		// 这里就去处理拿到的Token
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;

		String servletPath = httpRequest.getServletPath();

		// 判断是不是不需要验证的URL
		if (noUrlList.contains(servletPath)) {
			chain.doFilter(request, response);
			return;
		}

		// 去获取TOKEN，没有的话则直接返回401，然后提示未登录
		String token = httpRequest.getHeader(SysCfg.HEADER_TOKEN_KEY);

		// 判断是不是退出
		if (servletPath.equals(logoutUrl)) {
			// 处理token
			TokenLoginStore.remove(token);
			httpResponse.setStatus(HttpServletResponse.SC_OK);
			httpResponse.setContentType("application/json;charset=UTF-8");
			httpResponse.getWriter().println("{\"code\":" + Result.SUCCESS_CODE + ", \"msg\":\"" + "退出成功" + "\"}");
			return;
		}

		if (token == null || token == "") {
			httpResponse.setStatus(HttpServletResponse.SC_OK);
			httpResponse.setContentType("application/json;charset=UTF-8");
			httpResponse.getWriter().println("{\"code\":" + Result.NO_AUTHORIZATION + ", \"msg\":\"" + "未登录" + "\"}");
			return;
		}

		// 如果连接和退出衔接一样的话，清除Token
		if (servletPath.equals(logoutUrl)) {
			TokenLoginStore.remove(token);
			// 处理token
			httpResponse.setStatus(HttpServletResponse.SC_OK);
			httpResponse.setContentType("application/json;charset=UTF-8");
			httpResponse.getWriter().println("{\"code\":" + Result.SUCCESS_CODE + ", \"msg\":\"" + "退出成功" + "\"}");
			return;
		}

		// 有token，判断token在数据库有没有
		Boolean checkLaw = TokenLoginStore.checkLaw(token);
		if (!checkLaw) {
			System.out.println("#####违法，不通过校验");
			httpResponse.setStatus(HttpServletResponse.SC_OK);
			httpResponse.setContentType("application/json;charset=UTF-8");
			httpResponse.getWriter()
					.println("{\"code\":" + Result.NO_AUTHORIZATION + ", \"msg\":\"" + "非法请求，请立即停止操作" + "\"}");
			return;
		}
		// Token合法，判断在缓存有没有
		SSOUser ssoUser = TokenLoginStore.getRequestRedisKey(token);
		if (ssoUser == null) {
			httpResponse.setStatus(HttpServletResponse.SC_OK);
			httpResponse.setContentType("application/json;charset=UTF-8");
			httpResponse.getWriter()
					.println("{\"code\":" + Result.NO_AUTHORIZATION + ", \"msg\":\"" + "Token失效" + "\"}");
			return;
		}
		
		// 把这个用户信息返回回去
		ssoUser.setVersion("");
		request.setAttribute("authInfo", ssoUser);
		chain.doFilter(request, response);
	}

	@Override
	public void destroy() {

	}

	// ######################################
	public String getLogoutUrl() {
		return logoutUrl;
	}

	public HttpSSOTokenFilter setLogoutUrl(String logoutUrl) {
		this.logoutUrl = logoutUrl;
		return this;
	}

	public String getWithOutUrl() {
		return withOutUrl;
	}

	public HttpSSOTokenFilter setWithOutUrl(String withOutUrl) {
		this.withOutUrl = withOutUrl;
		try {
			String[] split = withOutUrl.split(",");
			noUrlList.addAll(Arrays.asList(split));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return this;
	}

}
