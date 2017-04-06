package com.elane.auth.controller.sys;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.elane.api.inside.service.UCenterService;
import com.elane.api.inside.vo.PasswordAuthVo;
import com.elane.common.constants.ElaneConstants;
import com.elane.common.utils.base.Base64;
import com.elane.common.utils.response.ElaneResponse;
import com.elane.common.utils.response.Meta;
import com.elane.core.controller.BaseController;
import com.elane.core.util.CookieUtil;
import com.elane.core.util.TokenUtil;

import net.sf.json.JSONObject;

/**
 * @author carl
 * 
 *    用户单点登录 
 *
 */
@Controller
@RequestMapping("/sys")
public class SSOLoginController extends BaseController{
    private final Logger logger = LoggerFactory.getLogger(SSOLoginController.class);
    
    /**
     * 用户名密码获取token
     * 
     * @param username
     * @param userpwd
     * @param respose 响应
     * @return json数据
     * */
    @RequestMapping("/ssologin")
    @ResponseBody
    public ElaneResponse sslogin(HttpServletResponse response,@RequestParam("username")String username,@RequestParam("userpwd")String userpwd){
        ElaneResponse result=new ElaneResponse();
        try {
            PasswordAuthVo autoVo=new PasswordAuthVo();
            autoVo.setUsername(username);
            autoVo.setPassword(userpwd);
            autoVo.setAuthorization("Basic " + Base64.encode(UCenterService.APPID+":"+UCenterService.APPKEY));
            
            //用户名 密码登录  获取access_token
            String tokenStr=UCenterService.passwordAuth(autoVo);
            JSONObject tokenJson=JSONObject.fromObject(tokenStr);
            if(tokenJson.getInt("responseCode")==200){
                //通过access_token获取openid
                String dataStr=UCenterService.getOpendId(tokenJson.getString("access_token"));
                JSONObject dataJson=JSONObject.fromObject(dataStr);
                
                //验证是否返回数据成功
                if(dataJson!=null&&dataJson.getInt("responseCode")==200&&dataJson.getString("IsSuccess").equals("true")){
                    JSONObject openIdJson=JSONObject.fromObject(dataJson.getString("Data"));
                    
                    logger.info("用户名密码登录用户中心接口成功,返回用户中心的用户openid为:"+openIdJson.getString("openid"));
                    //获取token 并写入到cookie当中
                    String token=TokenUtil.getJWTString(openIdJson.getString("openid"),
                                                    "http://drybulk.elane.com/auth/",
                                                    "carl@elane.com",
                                                    "drybulk.elane.com",
                                                    (60*60*1000));
                    //登录用户的cookie
                    CookieUtil.addCookie(response, "/", ElaneConstants.COOKIE_KEY_CURRENT_USER, token, (60*60));
                    
                    //用户中心提供的token 与用户中心交互使用
                    CookieUtil.addCookie(response, "/", ElaneConstants.COOKIE_KEY_UCENTER_ACCESS_TOKEN, tokenJson.getString("access_token"), (60*60));
                   
                    result.success(new Meta(dataJson.getInt("responseCode"),"登录成功！"));
                }else{
                    logger.error("通过接口获取用户中心中用户的用户openid失败，返回码："+dataJson.getInt("responseCode"));
                    result.success(new Meta(dataJson.getInt("responseCode"),"登录异常，请稍后重试！"));
                }
            }else{
                if(tokenJson.getInt("responseCode")==400||tokenJson.getInt("responseCode")==401){
                    result.success(new Meta(tokenJson.getInt("responseCode"),"用户名或密码错误！"));
                }
            }
        } catch (Exception e) {
            logger.error("调用用户中心password授权登录接口登录异常:",e);
            result.failure("登录异常，请联系管理员！");
            return result;
        }
        return result;
    }
    
    /**
     * 注销登录
     * 
     * @param respose 响应
     * 
     * */
    @RequestMapping("/ssologout")
    @ResponseBody
    public ElaneResponse ssologout(HttpServletRequest request,HttpServletResponse response){
        ElaneResponse result=new ElaneResponse();
        
        logger.info("用户中心openid为"+CookieUtil.getCookieByName(request, ElaneConstants.COOKIE_KEY_CURRENT_USER)+"的用户注销登录。");
        
        //清除cookie
        CookieUtil.removeCookie(response, "/", ElaneConstants.COOKIE_KEY_CURRENT_USER);
        
        result.success("注销成功");
        
        return result;
    }
}
