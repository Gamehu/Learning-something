package com.elane.core.bean;
/**
 * 
 * 登录用户信息
 * 
 * **/
public class ElaneUser {

    /***
     * 用户id
     */
    private String id;
    /***
     * 员工编码(业务主键)
     */
    private String code;
    /***
     * 员工姓名
     */
    private String name;
    /***
     * 关联组织机构
     */
    private String deptTid;
    /***
     * 平台企业编号
     */
    private String companyId;
    /***
     * 关联用户中心用户
     */
    private String userId;
    /***
     * 昵称
     */
    private String nickname;
    /***
     * 头像
     */
    private String image;
    /***
     * 0表示女，1表示男，默认男
     */
    private Integer sex;
    /***
     * 出生年月
     */
    private String bornDate;
    /***
     * 联系电话
     */
    private String telephone;
    /***
     * 电子邮件
     */
    private String email;
    /***
     * 微信号
     */
    private String wechatNo;
    /***
     */
    private String qQ;
    /***
     * 设置是否有效（1表示有效，0表示无效），默认为1
     */
    private Integer effective;
    /**
     * 公司名称
     * */
    private String companyName;
    /***
     * 登录网站的ID
     */
    private String webId;
    /**
     * 职务名称
     * */
    private String dutiesName;
    /**
     * 企业logo
     * */
    private String companyLogo;
    /**
     * 登录账号
     * */
    private String account;
    /**
     * 账号状态
     * */
    private Integer verification;
    /**
     * 客户端类型  0:PC端 1:APP端（默认0）
     */
    private Integer clientType = 0;
    
    public String getCompanyName() {
        return companyName;
    }
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
    public String getWebId() {
        return webId;
    }
    public void setWebId(String webId) {
        this.webId = webId;
    }
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getCode() {
        return code;
    }
    public void setCode(String code) {
        this.code = code;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getDeptTid() {
        return deptTid;
    }
    public void setDeptTid(String deptTid) {
        this.deptTid = deptTid;
    }
    public String getCompanyId() {
        return companyId;
    }
    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }
    public String getUserId() {
        return userId;
    }
    public void setUserId(String userId) {
        this.userId = userId;
    }
    public String getNickname() {
        return nickname;
    }
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
    public String getImage() {
        return image;
    }
    public void setImage(String image) {
        this.image = image;
    }
    public Integer getSex() {
        return sex;
    }
    public void setSex(Integer sex) {
        this.sex = sex;
    }
    public String getBornDate() {
        return bornDate;
    }
    public void setBornDate(String bornDate) {
        this.bornDate = bornDate;
    }
    public String getTelephone() {
        return telephone;
    }
    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getWechatNo() {
        return wechatNo;
    }
    public void setWechatNo(String wechatNo) {
        this.wechatNo = wechatNo;
    }
    public String getqQ() {
        return qQ;
    }
    public void setqQ(String qQ) {
        this.qQ = qQ;
    }
    public Integer getEffective() {
        return effective;
    }
    public void setEffective(Integer effective) {
        this.effective = effective;
    }
    public String getDutiesName() {
        return dutiesName;
    }
    public void setDutiesName(String dutiesName) {
        this.dutiesName = dutiesName;
    }
    public String getCompanyLogo() {
        return companyLogo;
    }
    public void setCompanyLogo(String companyLogo) {
        this.companyLogo = companyLogo;
    }
    public String getAccount() {
        return account;
    }
    public void setAccount(String account) {
        this.account = account;
    }
    public Integer getVerification() {
        return verification;
    }
    public void setVerification(Integer verification) {
        this.verification = verification;
    }
    public Integer getClientType() {
        return clientType;
    }
    public void setClientType(Integer clientType) {
        this.clientType = clientType;
    }
    
}
