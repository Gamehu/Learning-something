package com.elane.api.inside.vo;

public class IdTokenGetVO {

	/*
	 *  grant_type 为必填，固定值：authorization_code
	 */
	private final String grant_type = "authorization_code";
	
	/*
	 * client_id 为必填，项目在用户中心的appid
	 */
	private String client_id;
	
	/*
	 * client_secret 为必填，项目在用户中心appkey
	 */
	private String client_secret;
	
	/*
	 * redirect_uri 为必填，与请求时的一致
	 */
	private String redirect_uri;
	
	/*
	 * code 为必填
	 */
	private String code;

	public String getGrant_type() {
		return grant_type;
	}

	public String getClient_id() {
		return client_id;
	}

	public void setClient_id(String client_id) {
		this.client_id = client_id;
	}

	public String getClient_secret() {
		return client_secret;
	}

	public void setClient_secret(String client_secret) {
		this.client_secret = client_secret;
	}

	public String getRedirect_uri() {
		return redirect_uri;
	}

	public void setRedirect_uri(String redirect_uri) {
		this.redirect_uri = redirect_uri;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
}
