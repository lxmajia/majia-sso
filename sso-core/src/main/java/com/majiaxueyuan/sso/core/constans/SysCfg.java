package com.majiaxueyuan.sso.core.constans;

public interface SysCfg {

	String LUCKLY_NUM = "520";

	// majia-sso#5201
	String STORE_TOKEN_PREFIX = "majia-sso" + LUCKLY_NUM;
	String STORE_TOKEN_SPLIT = "#";

	// majia-session-sso-key5201.js5781515f1d4fs45dFsdf
	// 1就相当于唯一ID
	// 可以通过上面的找到KEY
	String USER_TOKEN_PREFIX = "majia-session-sso-key" + LUCKLY_NUM;
	String USER_TOKEN_SPILIT = ".";

	String HEADER_TOKEN_KEY = "Authorization";

	String SSO_SERVER_PATH = "sso_server_path";
	String SSO_LOGOUT_URL = "sso_logout_url";
}
