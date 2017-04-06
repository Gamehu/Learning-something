package com.elane.api.inside.vo;
/**
 *
 * @description
 *          企业用户密码修改vo
 * @author carl
 *
 * @version 创建时间：2016年11月23日 下午3:45:56
 * 
 **/
public class UpdateUserPwdVO {
    private String access_token;//token 必填
    private String openid;//用户ID 必填
    private String oldpwd;//旧密码 必填
    private String newpwd;//新密码 必填
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
    public String getOldpwd() {
        return oldpwd;
    }
    public void setOldpwd(String oldpwd) {
        this.oldpwd = oldpwd;
    }
    public String getNewpwd() {
        return newpwd;
    }
    public void setNewpwd(String newpwd) {
        this.newpwd = newpwd;
    }
    
    
}
