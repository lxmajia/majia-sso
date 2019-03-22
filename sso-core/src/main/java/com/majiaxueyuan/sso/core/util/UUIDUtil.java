package com.majiaxueyuan.sso.core.util;

import java.util.UUID;

public class UUIDUtil {
	public static String rand() {
		return UUID.randomUUID().toString().replace("-", "");
	}
}
