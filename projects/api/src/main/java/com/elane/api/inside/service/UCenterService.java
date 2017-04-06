package com.elane.api.inside.service;

import org.apache.commons.lang3.StringUtils;

import net.sf.json.JSONObject;

import com.elane.api.inside.vo.CreateCompanyUserVO;
import com.elane.api.inside.vo.IdTokenGetVO;
import com.elane.api.inside.vo.PasswordAuthVo;
import com.elane.api.inside.vo.ResetUserPwdVo;
import com.elane.api.inside.vo.UpdateUserPwdVO;
import com.elane.common.utils.base.Base64;
import com.elane.core.util.HttpRequestUtil;

/**
 *
 * @description 此类为用户中心接口服务类，己方将用户中心提供的接口集成服务类
 *
 * @author carl
 *
 * @version 创建时间：2016年11月22日 下午3:54:40
 * 
 **/
public class UCenterService {
    //项目在用户中心的appid
    public static String APPID="EU20161025001";
    //项目在用户中心appkey
    public static String APPKEY="c1073dd247ae49a7a437e13af4bb711a";
    
    
    //注册企业用户接口
    private static String create_user_url="http://ucenter.shipxy.com/UCAPI/CreateCompanyUser";
    //token授权   grant_type=password(密码授权)/client_credentials(客户端授权)
    private static String get_token_url="http://ucenter.shipxy.com/OAuth/Token";
    //获取登录用户openid
    private static String get_openid_url="http://ucenter.shipxy.com/OAuth/Me";
    //修改企业用户的密码
    private static String update_company_userpwd_url="http://ucenter.shipxy.com/UCAPI/UpdateCompanyUserPwd";
    //重置企业用户的密码
    private static String reset_company_userpwd_url="http://ucenter.shipxy.com/UCAPI/ResetCompanyUserPwd";
    //获取企业用户信息
    private static String get_company_user_url="http://ucenter.shipxy.com/UCAPI/GetCompanyUser";
    //用户修改手机号
    private static String user_change_mobile_url="http://ucenter.shipxy.com/UCAPI/UserChangeMobile";
    // 通过code获取ACCESS_TOKEN
    private static String code_get_access_token_url="https://id.shipxy.com/core/connect/token";
    
    /**
     * 注册一个新的企业用户
     * @description    不能注册企业的管理员用户，传递过来的密码用明文，只有企业的管理员才能使用此接口注册新的用户
     * @param userVo.access_token       token
     * @param userVo.username          用户名  必填
     * @param userVo.userpwd           明文密码  必填
     * @param userVo.openidofcreator   创建者的openid 必填
     * @param userVo.mobile            手机号  非必填
     * @param userVo.email             电子邮件 非必填
     * @return  <h2>返回信息</h2>
     *          <p><1> {"status":1,"msg":"成功","openid":"openid"}</p>
     *          <p><2> {"status":0,"msg":"未知的异常"}</p>
     *          <p><3> {"status":-1,"msg":"企业不存在"}</p>
     *          <p><4> {"status":-2,"msg":"请先创建管理员/管理员已存在"}</p>
     *          <p><5> {"status":-3,"msg":"用户名已存在"}</p>
     *          <p><6> {"status":-4,"msg":"手机号已存在"}</p>
     *          <p><7> {"status":-5,"msg":"电子邮件已存在"}</p>
     */
    public static String createCompanyUser(CreateCompanyUserVO userVo){
        String param=HttpRequestUtil.assembleURLParams(userVo);
        System.out.println(param);
        String result = HttpRequestUtil.sendPost(create_user_url, param );
        
        return result;
    }
    
    
    /**
     * 通过password授权
     * @description 需要POST提交
     * @param autoVo.grant_type      password固定值 必填
     * @param autoVo.username        用户名 必填
     * @param autoVo.password        用户密码 必填
     * @param autoVo.Authorization   Basic Base64("client_id:client_secret") [注意:Basic后有一个空格] 必填
     * @return <h2>返回信息</h2> 
     *         <p><1> {"access_token":"{access_token}","token_type":"bearer","expires_in":2591999}</p>
     */
    public static String passwordAuth(PasswordAuthVo autoVo){
        String param=HttpRequestUtil.assembleURLParams(autoVo);
        String result = HttpRequestUtil.sendPost(get_token_url, param );
        return result;
    }
    
    
    /**
     * 获取OpenID
     * @param accessToken token 必填
     * @param openid 用户openid 必填
     * @return <h2>返回信息</h2>
     *         <p><1> {"Data":{"client_id":"xxxxxxx","openid":"xxxxxx"},"Message":"","IsSuccess":true}</p>
     *         <p><2> {"Data":{"client_id":"xxxxxxx","openid":"xxxxxx"},"Message":"","IsSuccess":false}</p>
     */
    public static String getCompanyUser(String accessToken,String openid){
        String param="access_token="+accessToken+"&openid="+openid;
        String result = HttpRequestUtil.sendPost(get_company_user_url, param );
        return result;
    }
    /**
     * 获取企业用户信息
     * @param accessToken token 必填
     * @return <h2>返回信息</h2>
     *         <p><1> {"status":1,"msg":"成功","data": {"CompanyID": "e515d4c8-2773-4045-9316-f833d131d9bc","UserName": "test","IsAdmin": 1,"Enabled": 1,"Remark": "张三哥","CreateTime": "2016-05-23 13:57:06","openid": "96ae9143d2c70e09"}}</p>
     *         <p><1> {"status":0,"msg":"未知的异常"}</p>
     *         <p><2> {"status":-1,"msg":"必填参数不能为空"}</p>
     *         <p><2> {"status":-2,"msg":"没有找到这个用户"}</p>
     */
    public static String getOpendId(String accessToken){
        String result = HttpRequestUtil.sendPost(get_openid_url, "access_token="+accessToken);
        return result;
    }
    /**
     * 获取客户端授权token
     * @param grant_type        授权类型  固定值：client_credentials 必填
     * @param authorization     Basic Base64("client_id:client_secret") 注意Basic后有一个空格
     * @return <h2>返回信息</h2>
     *         <p><1> {"access_token":"{access_token}","token_type":"bearer","expires_in":2591999}</p>
     */
    public static String getClientToken(String grant_type,String authorization){
        if(StringUtils.isEmpty(grant_type)){
            grant_type="client_credentials";
        }
        if(StringUtils.isEmpty(authorization)){
            authorization="Basic "+Base64.encode(APPID+":"+APPKEY);
        }
        String param="grant_type="+grant_type+"&authorization=" + authorization;
        String result = HttpRequestUtil.sendPost(get_token_url, param );
        return result;
    }
    /**
     * 修改企业用户的密码
     * 
     * @param pwdVo.access_token token 必填
     * @param pwdVo.userid  用户ID 必填
     * @param pwdVo.oldpwd  旧密码  必填
     * @param pwdVo.newpwd  新密码  必填
     * @return  <h2>返回信息</h2>
     *          <p><1> {"status":1,"msg":"成功"}</p>
     *          <p><2> {"status":0,"msg":"未知的异常"}</p>
     *          <p><3> {"status":-1,"msg":"必填参数不能为空"}</p>
     *          <p><4> {"status":-2,"msg":"账户不存在"}</p>
     *          <p><5> {"status":-3,"msg":"原密码错误"}</p>
     *          <p><6> {"status":-11,"msg":"无效的业务系统key"}</p>
     *          <p><7> {"status":-12,"msg":"存在非法cpu，服务器拒绝访问"}</p>
     *          <p><8> {"status":-13,"msg":"存在非法ip或mac，服务器拒绝访问"}</p>
     *          <p><9> {"status":"-14","msg":"来源域或IP来源域或IP无权访问"}</p>
     */
    public static String updateCompanyUserPwd(UpdateUserPwdVO pwdVo){
        String param=HttpRequestUtil.assembleURLParams(pwdVo);
        String result = HttpRequestUtil.sendPost(update_company_userpwd_url, param );
        return result;
    }
    
    /**
     * 重置企业用户的密码
     * @param pwdVo.access_token token 必填
     * @param pwdVo.userid       用户ID 必填
     * @param pwdVo.pwd          密码 必填
     * @return <h2>返回信息</h2>
     *         <p><1> {"status":1,"msg":"重置成功"}</p>
     *         <p><2> {"status":0,"msg":"重置失败"}</p>
     *         <p><3> {"status":-1,"msg":"必填参数不能为空"}</p>
     *         <p><4> {"status":-2,"msg":"账户不存在"}</p>
     *         <p><5> {"status":-11,"msg":"无效的业务系统key"}</p>
     *         <p><6> {"status":-12,"msg":"存在非法cpu，服务器拒绝访问"}</p>
     *         <p><7> {"status":-13,"msg":"存在非法ip或mac，服务器拒绝访问"}</p>
     *         <p><8> {"status":"-14","msg":"来源域或IP来源域或IP无权访问"}</p>
     */
    public static String resetCompanyUserPwd(ResetUserPwdVo pwdVo){
        String param=HttpRequestUtil.assembleURLParams(pwdVo);
        String result = HttpRequestUtil.sendPost(reset_company_userpwd_url, param );
        return result;
    }
    /**
     * 用户修改手机号      role=user
     * @param accessToken   用户交互token
     * @param openid        用户openid
     * @param mobile        手机号
     * @return  <h2>返回信息</h2>
     *          <p><1> {"status":1,"msg":"成功"}</p>
     *          <p><2> {"status":0,"msg":"未知的异常"}</p>
     *          <p><3> {"status":-1,"msg":"必填参数不能为空"}</p>
     *          <p><4> {"status":-2,"msg":"手机号格式错误"}</p>
     *          <p><5> {"status":-3,"msg":"手机号已注册"}</p>
     *          <p><6> {"status":-4,"msg":"没有找到用户"}</p>
     *
     */
    public static String userChangeMobile(String accessToken,String openid,String mobile){
        String param="access_token="+accessToken+"&openid="+openid+"&mobile="+mobile;
        String result = HttpRequestUtil.sendPost(user_change_mobile_url, param );
        return result;
    }
    
    /**
     * 获取 id_token
     * @param idTokenGetVO
     * @param idTokenGetVO.grant_type	必填，固定值：authorization_code
     * @param idTokenGetVO.client_id	必填，项目在用户中心的appid
     * @param idTokenGetVO.client_secret	必填，项目在用户中心appkey
     * @param idTokenGetVO.redirect_uri	必填，与请求时的一致
     * @param idTokenGetVO.code	必填
     * @return {
				"id_token": "{id_token}",
				"access_token": "{access_token}",
				"expires_in": 3600,
				"token_type": "Bearer"
				} 
     */
    public static String getIdToken(IdTokenGetVO idTokenGetVO){
    	idTokenGetVO.setClient_id(APPID);
    	idTokenGetVO.setClient_secret(APPKEY);
    	String param=HttpRequestUtil.assembleURLParams(idTokenGetVO);
    	System.out.println(param);
    	String result = HttpRequestUtil.sendPost(code_get_access_token_url, param );
    	return result;
    }
    
    public static void main(String[] args) {
        //获取客户端token   即系统token
        String clientToken=UCenterService.getClientToken("","");
        JSONObject clientJson=JSONObject.fromObject(clientToken);
        
        //用户登录
        PasswordAuthVo authVo=new PasswordAuthVo();
        authVo.setUsername("业务@elane");//yhl@elane  admin@dlyx
        authVo.setPassword("111111");//111111
        authVo.setAuthorization("Basic " + Base64.encode(APPID+":"+APPKEY));
        String tokenStr=UCenterService.passwordAuth(authVo);
        System.out.println(tokenStr);
        JSONObject tokenJson=JSONObject.fromObject(tokenStr);
        
        
        //获取登录用户openid
        String openidStr=UCenterService.getOpendId(tokenJson.getString("access_token"));
        JSONObject penidJson=JSONObject.fromObject(openidStr);
        JSONObject openId=JSONObject.fromObject(penidJson.getString("Data"));
        System.out.println(openId.getString("openid"));
        
        String s=UCenterService.getCompanyUser(clientJson.getString("access_token"), "24a0ede8161065e9");
        System.out.println(s);
        //修改用户密码
        //UpdateUserPwdVO pwdVo=new UpdateUserPwdVO();
        //pwdVo.setAccess_token(tokenJson.getString("access_token"));
        //pwdVo.setOpenid(openId.getString("openid"));
        //pwdVo.setOldpwd("111111");
        //pwdVo.setNewpwd("111111");
        //String result=UCenterService.updateCompanyUserPwd(pwdVo);
        //System.out.println(result);
        
        
        //重置密码
        //ResetUserPwdVo resetVo=new ResetUserPwdVo();
        //resetVo.setAccess_token(tokenJson.getString("access_token"));
        //resetVo.setOpenid(openId.getString("openid"));
        //resetVo.setPwd("111111");
        //String result1=UCenterService.resetCompanyUserPwd(resetVo);
        //System.out.println(result1);
        
        
        
        //创建用户
        //CreateCompanyUserVO user=new CreateCompanyUserVO();
        //user.setAccess_token(tokenJson.getString("access_token"));
        //user.setEmail("yewu@elane");
        //user.setUsername("业务");
        //user.setMobile("13731177621");
        //user.setOpenidofcreator(openId.getString("openid"));
        //user.setUserpwd("111111");
        //String result2=UCenterService.createCompanyUser(user);
        //System.out.println(result2);
    }
}
