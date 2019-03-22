package com.majiaxueyuan.sso.core.util;

import redis.clients.jedis.Jedis;

// 操作Redis
public class JedisUtil {

	private static String address;
	private static int port;

	private static Jedis jedis;

	public static void main(String[] args) {
		// 连接本地的 Redis 服务
		Jedis jedis = new Jedis("127.0.0.1");
		// jedis.select(1);
		System.out.println("Connection to server sucessfully");
		// 查看服务是否运行
		System.out.println("Server is running: " + jedis.ping());
		String string = jedis.get("xxl_sso_sessionid#1000");
		System.out.println(string);
	}

	public static void init(String host, Integer port) {
		JedisUtil.address = host;
		JedisUtil.port = port;

		getInstans(JedisUtil.address, JedisUtil.port);
	}

	public static Jedis getInstans(String host, Integer port) {
		if (jedis == null) {
			jedis = new Jedis(host, port);
		}
		return jedis;
	}

	public static void close() {
		jedis.close();
	}

	public static void setKey(String key, String value) {
		jedis.set(key, value);
	}

	public static void removeKey(String key) {
		jedis.del(key);
	}

	public static String getValue(String key) {
		return jedis.get(key);
	}
}
