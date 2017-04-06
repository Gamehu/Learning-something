package com.elane.common.utils.response;

public class Meta {

	private Integer status;
	private String message;

	public Meta(Integer success) {
		this.status = success;
		this.message = null;
	}

	public Meta(Integer success, String message) {
		this.status = success;
		this.message = message;
	}

	public Integer getStatus() {
		return status;
	}

	public String getMessage() {
		return message;
	}

}
