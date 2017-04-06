package com.elane.api.inside.vo;
/**
 *
 * @description
 *          注册一个新的企业用户vo
 * @author carl
 *
 * @version 创建时间：2016年11月22日 下午4:21:56
 * 
 **/
public class CreateCompanyUserVO{
    /**
     * token
     */
    private String access_token;
    /**
     * 用户名  必填
     */
    private String username;
    /**
     * 明文密码  必填
     */
    private String userpwd;
    /**
     * 创建者的openid 必填
     */
    private String openidofcreator;
    /**
     * 手机号  非必填
     */
    private String mobile;
    /**
     * 电子邮件 非必填
     */
    private String email;
    
    public String getAccess_token() {
        return access_token;
    }
    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getUserpwd() {
        return userpwd;
    }
    public void setUserpwd(String userpwd) {
        this.userpwd = userpwd;
    }
    public String getOpenidofcreator() {
        return openidofcreator;
    }
    public void setOpenidofcreator(String openidofcreator) {
        this.openidofcreator = openidofcreator;
    }
    public String getMobile() {
        return mobile;
    }
    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    
}
