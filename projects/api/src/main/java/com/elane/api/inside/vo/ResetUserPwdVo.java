package com.elane.api.inside.vo;
/**
 *
 * @description
 *          重置企业用户的密码vo
 * @author carl
 *
 * @version 创建时间：2016年11月23日 下午4:04:57
 * 
 **/
public class ResetUserPwdVo {
    private String access_token;//token 必填
    private String openid;//用户ID 必填
    private String pwd;//密码  必填
    public String getAccess_token() {
        return access_token;
    }
    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }
    
    public String getOpenid() {
        return openid;
    }
    public void setOpenid(String openid) {
        this.openid = openid;
    }
    public String getPwd() {
        return pwd;
    }
    public void setPwd(String pwd) {
        this.pwd = pwd;
    }
    
    
}
