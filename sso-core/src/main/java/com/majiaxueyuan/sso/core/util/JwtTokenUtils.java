package com.majiaxueyuan.sso.core.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.majiaxueyuan.sso.core.constans.SysCfg;
import com.majiaxueyuan.sso.core.entity.SSOUser;

public class JwtTokenUtils {

	public static String createToken(SSOUser user) {
		String token = SysCfg.TOKEN_LOGO + JWT.create().withSubject(JSON.toJSONString(user))
				.withAudience(user.getId() + "").sign(Algorithm.HMAC256(SysCfg.TOKEN_SALT));
		return token;
	}

	public static Boolean checkTokenInLaw(String token) {
		if (!token.startsWith(SysCfg.TOKEN_LOGO)) {
			return false;
		}
		token = token.substring(SysCfg.TOKEN_LOGO.length(), token.length());
		JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(SysCfg.TOKEN_SALT)).build();
		try {
			jwtVerifier.verify(token);
			return true;
		} catch (JWTVerificationException e) {
			return false;
		}
	}

	public static SSOUser getTokenSSOUser(String token) {
		token = token.substring(SysCfg.TOKEN_LOGO.length(), token.length());
		String subject = JWT.decode(token).getSubject();
		SSOUser ssoUser = JSONObject.parseObject(subject, SSOUser.class);
		return ssoUser;
	}
}
