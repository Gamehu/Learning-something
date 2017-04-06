package com.elane.core.interceptor;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.elane.common.constants.ElaneConstants;
import com.elane.common.utils.response.ElaneResponse;
import com.elane.core.bean.ElaneUser;
/***
 * 拦截器
 * @author Administrator
 *
 */
public class LoginInterceptor extends HandlerInterceptorAdapter {

	private final Logger logger = LoggerFactory.getLogger(LoginInterceptor.class);
	
	@Autowired
	protected HttpSession httpSession;

	private List<String> excluderUrls;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		String url = request.getRequestURL().toString().trim();
		// 免拦截URL校验
		if (null != excluderUrls && excluderUrls.size() > 0) {
			for (String eu : excluderUrls) {
				if (url.contains(eu)) {
					return true;
				}
			}
		}

		logger.debug(String.format("拦截器LoginInterceptor读取当前sessionid = 【%s】", httpSession.getId()));
		ElaneUser user = (ElaneUser) httpSession.getAttribute(ElaneConstants.SESSION_KEY_CURRENT_USER);
		
		/*判断是否为ajax请求json数据(空返回界面，非空返回json)*/
		
		Object isResponseBody = null;
		//请求的地址500时，handlerMethod找不到对应方法，强制转换异常，返回true，web.xml配置500的异常处理
		try {
		    isResponseBody = ((HandlerMethod)handler).getMethodAnnotation(ResponseBody.class);
        } catch (Exception e) {
        }
        
        if (null == user) {
            if(isResponseBody != null){
                response.setContentType("application/json;charset=utf-8");
                response.setStatus(200);
                response.getWriter().write(JSONObject.fromObject("{key:\"noUser\"}").toString());
                response.flushBuffer();
                return false;
            }else{
                httpSession.invalidate();
                request.getRequestDispatcher("/view/login.jsp").forward(request, response);
                return false;
            }
        }

		return true;
	}

	public List<String> getExcluderUrls() {
		return excluderUrls;
	}

	public void setExcluderUrls(List<String> excluderUrls) {
		this.excluderUrls = excluderUrls;
	}
	
	/**
	 * controller抛出异常时不执行postHandle
	 */
	@Override
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		//以下仅处理返回界面的情况
		if(modelAndView != null && modelAndView.getViewName() != null){
			ModelMap modelMap = modelAndView.getModelMap();
			//获取Model中设置的值，验证是否异常
			ElaneResponse elaneResponse  = (ElaneResponse) modelMap.get("elaneResponse");
			//出现异常情况
			if(elaneResponse != null && elaneResponse.getMeta().getStatus() == ElaneResponse.UNEXPECT ){
			    //重定向会看到jsp的路径
				//response.sendRedirect("/drybulk-web/view/exception/unexpect.jsp");
			    //转发浏览器还是原来地址
				request.getRequestDispatcher("/view/exception/unexpect.jsp").forward(request, response);
			}
		}
	}
}
