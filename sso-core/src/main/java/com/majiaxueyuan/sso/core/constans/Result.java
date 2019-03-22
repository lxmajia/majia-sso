package com.majiaxueyuan.sso.core.constans;

public class Result {
	private Integer code;
	private String msg;
	private Object data;

	public static int SUCCESS_CODE = 200;
	public static int NO_AUTHORIZATION = 401;
	public static int NOT_ALLOW = 502;

	public static Result success(Object content) {
		Result result = new Result();
		result.setCode(SUCCESS_CODE);
		result.setMsg("SUCCESS");
		result.setData(content);
		return result;
	}

	public static Result failed(String msg) {
		Result result = new Result();
		result.setCode(NOT_ALLOW);
		result.setMsg("NOT_ALLOW");
		result.setData("用户唯一ID为空");
		return result;
	}

	public static Result noAuth() {
		Result result = new Result();
		result.setCode(NO_AUTHORIZATION);
		result.setMsg("没有认证，或认证失效");
		result.setData(null);
		return result;
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

}
