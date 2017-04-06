package com.elane.logger.controller;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

import org.mitre.openid.connect.model.OIDCAuthenticationToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.elane.common.utils.response.ElaneResponse;
import com.elane.logger.utils.ConfManger;
import com.elane.logger.utils.RestTemplateUtils;

/**
 * 后台查看日志
 */
@RestController
@RequestMapping("/logview")
public class LogViewController {

	/*当前controller注解添加属性produces="application/json; charset=utf-8"原因：
	请求接口正常，返回的数据，包含ElaneResponse信息，直接返回；
	请求接口异常，方法内封装ElaneResponse信息返回。方法的返回类型为object，js解析返回的json数据，出现读取属性异常。设置produces解决*/
	private final Logger logger = LoggerFactory.getLogger(LogViewController.class);
	
	@RequestMapping(value="/log", produces="application/json; charset=utf-8")
	@PreAuthorize("hasRole('ROLE_USER')")
	public Object log(Principal p, String app_id, String company_id, String user_id, 
			String database, String table, String tsBegin, String tsEnd, String pageNo) {
		OIDCAuthenticationToken token = (OIDCAuthenticationToken) p;
		
		String url = ConfManger.getValue("log.api.host") + ConfManger.getValue("log.api.loglist");
		Map<String, String> parameter = new HashMap<String, String>();
		parameter.put("access_token", token.getAccessTokenValue());
		parameter.put("app_id", app_id);
		parameter.put("company_id", company_id);
		parameter.put("user_id", user_id);
		parameter.put("database", database);
		parameter.put("table", table);
		parameter.put("tsBegin", tsBegin);
		parameter.put("tsEnd", tsEnd);
		parameter.put("pageNo", pageNo);
		
		String result = "";
		try {
			result = RestTemplateUtils.restRequest(url, parameter, null);
			if(result == null){
				logger.error("请求日志中心API获取数据失败");
				return new ElaneResponse().failure();
			}
		} catch (Exception e) {
			logger.error("请求日志中心API异常");
			return new ElaneResponse().failure();
		}
		return result;
	}
	
	@RequestMapping(value="/transaction", produces="application/json; charset=utf-8")
	@PreAuthorize("hasRole('ROLE_USER')")
	public Object transaction(Principal p, String app_id, String xtransaction_id, String tsBegin, String tsEnd, String pageNo) {
		OIDCAuthenticationToken token = (OIDCAuthenticationToken) p;
		
		String url = ConfManger.getValue("log.api.host") + ConfManger.getValue("log.api.transaction");
		Map<String, String> parameter = new HashMap<String, String>();
		parameter.put("access_token", token.getAccessTokenValue());
		parameter.put("app_id", app_id);
		parameter.put("xtransaction_id", xtransaction_id);
		parameter.put("tsBegin", tsBegin);
		parameter.put("tsEnd", tsEnd);
		parameter.put("pageNo", pageNo);
		
		String result = "";
		try {
			result = RestTemplateUtils.restRequest(url, parameter, null);
			if(result == null){
				logger.error("请求日志中心API获取数据失败");
				return new ElaneResponse().failure();
			}
		} catch (Exception e) {
			logger.error("请求日志中心API异常");
			return new ElaneResponse().failure();
		}
		return result;
	}
}
