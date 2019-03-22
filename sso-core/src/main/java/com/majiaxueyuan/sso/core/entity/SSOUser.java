package com.majiaxueyuan.sso.core.entity;

import java.io.Serializable;

public class SSOUser implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7627538371743609929L;
	/**
	 * 
	 */
	private Long id;
	private String username;
	private String other;
	private String version;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getOther() {
		return other;
	}

	public void setOther(String other) {
		this.other = other;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "SSOUser [id=" + id + ", username=" + username + ", other=" + other + "]";
	}

}
