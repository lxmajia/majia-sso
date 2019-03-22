package com.majiaxueyuan.sso.core.store;

import java.io.IOException;

import com.majiaxueyuan.sso.core.constans.SysCfg;
import com.majiaxueyuan.sso.core.entity.SSOUser;
import com.majiaxueyuan.sso.core.util.JedisUtil;
import com.majiaxueyuan.sso.core.util.SerUtil;

public class TokenLoginStore {

	public static String storeToken(SSOUser user) {
		String serialize = SerUtil.serialize(user);
		String redisKey = getRedisKey(user.getId());
		String sessionKey = getSessionKey(user.getId(), user.getVersion());
		// 保存用户
		JedisUtil.setKey(redisKey, serialize);
		// 返回sessionKey
		return sessionKey;
	}

	public static Boolean checkLaw(String token) {
		if (!token.startsWith(SysCfg.USER_TOKEN_PREFIX)) {
			return false;
		}
		try {
			token.split("\\.")[1].length();
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public static SSOUser getRequestRedisKey(String token) {
		String mainToken = token.substring(SysCfg.USER_TOKEN_PREFIX.length(), token.length());

		String[] tokenSplit = mainToken.split("\\.");
		String uniqueId = tokenSplit[0];
		String version = tokenSplit[1];

		String redisKey = getRedisKey(Long.parseLong(uniqueId));
		String redisValue = JedisUtil.getValue(redisKey);
		if (redisValue == null) {
			return null;
		}

		SSOUser user = SerUtil.deserialize(redisValue);
		if (!user.getVersion().equals(version)) {
			return null;
		}
		return user;
	}

	private static String getRedisKey(Long id) {
		String redisKey = SysCfg.STORE_TOKEN_PREFIX + SysCfg.STORE_TOKEN_SPLIT + id;
		return redisKey;
	}

	private static String getSessionKey(Long id, String version) {
		String sessionKey = SysCfg.USER_TOKEN_PREFIX + id + SysCfg.USER_TOKEN_SPILIT + version;
		return sessionKey;
	}

	public static void remove(String token) {
		if (token == null || token.equals("")) {
			return;
		}
		try {
			String mainToken = token.substring(SysCfg.USER_TOKEN_PREFIX.length(), token.length());
			String[] tokenSplit = mainToken.split("\\.");
			String uniqueId = tokenSplit[0];
			String redisKey = getRedisKey(Long.parseLong(uniqueId));
			JedisUtil.removeKey(redisKey);
		} catch (Exception e) {

		}
	}
}
