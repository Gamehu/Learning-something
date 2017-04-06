package com.elane.smsTrans.psms.entity;

public class Request {
	
	public Request() {

	}	

	public Request(String phone, String content) {
		super();
		this.phone = phone;
		this.content = content;
	}
	//TODO 参数从配置文件读
	private final static short type = 102;
	private final static String groupID = "KuaiCang"; 
	private final static short internalType = 10;
	private String phone;
	private String content;
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public static short getType() {
		return type;
	}
	public static String getGroupid() {
		return groupID;
	}
	public static short getInternaltype() {
		return internalType;
	}
 
}
