package com.elane.api.inside.vo;
/**
 *
 * @description password授权认证登录
 *
 * @author carl
 *
 * @version 创建时间：2016年11月22日 下午4:41:55
 * 
 **/
public class PasswordAuthVo {
    /**
     * password固定值 必填
     */
    private String grant_type="password"; 
    /**
     * 用户名 必填
     */
    private String username;
    /**
     * 用户密码 必填
     */
    private String password;
    /**
     * Basic Base64("{client_id}:{client_secret}") [注意:Basic后有一个空格] 必填
     */
    private String Authorization;
    public String getGrant_type() {
        return grant_type;
    }
    public void setGrant_type(String grant_type) {
        this.grant_type = grant_type;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getAuthorization() {
        return Authorization;
    }
    public void setAuthorization(String authorization) {
        Authorization = authorization;
    }
    
}
