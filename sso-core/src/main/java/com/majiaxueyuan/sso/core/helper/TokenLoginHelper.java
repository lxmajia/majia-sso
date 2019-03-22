package com.majiaxueyuan.sso.core.helper;

import com.majiaxueyuan.sso.core.constans.Result;
import com.majiaxueyuan.sso.core.entity.SSOUser;
import com.majiaxueyuan.sso.core.store.TokenLoginStore;
import com.majiaxueyuan.sso.core.util.UUIDUtil;

// 这里做登录成功过后的处理
public class TokenLoginHelper {

	public static Result loginSuccess(Long userId, String username, String otherParam) {
		if (userId == null) {
			return Result.failed("uniqueId不能为空");
		}
		// 到这里表示当前用户登录成功
		SSOUser user = new SSOUser();
		user.setId(userId);
		user.setUsername(username);
		user.setOther(otherParam);
		// 这里要去生成一个TOKEN
		String uuid = UUIDUtil.rand();
		user.setVersion(uuid);
		// 存储token
		String sessionToken = TokenLoginStore.storeToken(user);
		return Result.success(sessionToken);
	}
}
