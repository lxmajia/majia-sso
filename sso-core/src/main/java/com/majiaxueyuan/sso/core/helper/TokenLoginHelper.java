package com.majiaxueyuan.sso.core.helper;

import com.majiaxueyuan.sso.core.constans.Result;
import com.majiaxueyuan.sso.core.constans.SysCfg;
import com.majiaxueyuan.sso.core.entity.SSOUser;
import com.majiaxueyuan.sso.core.util.JwtTokenUtils;

// 这里做登录成功过后的处理
public class TokenLoginHelper {

	public static Result loginSuccess(Long userId, String username, String otherParam, String tokenSalt) {
		if (tokenSalt != null && !tokenSalt.equals("")) {
			if (!SysCfg.TOKEN_SALT.equals(tokenSalt)) {
				SysCfg.TOKEN_SALT = tokenSalt;
			}
		}

		// 利用读取的配置
		if (userId == null) {
			return Result.failed("uniqueId不能为空");
		}
		// 到这里表示当前用户登录成功
		SSOUser user = new SSOUser();
		user.setId(userId);
		user.setUsername(username);
		user.setOther(otherParam);
		// 生成Token
		String token = JwtTokenUtils.createToken(user);
		return Result.success(token);
	}
}
