package com.elane.logger.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.elane.common.utils.response.ElaneResponse;
import com.elane.logger.dao.entity.LogEntity;
import com.elane.logger.dao.templete.Page;
import com.elane.logger.dao.vo.LogSearch;
import com.elane.logger.service.LogServiceImpl;

/**
 * 定义受限的API
 */
@RestController
@RequestMapping("/api")
public class LogController {

	@Resource
	private LogServiceImpl service;
	
	/**
	 * 查询日志信息
	 * @return
	 */
	@RequestMapping("/loglist")
	public ElaneResponse logdata(String app_id, String company_id, String user_id, 
			String database, String table, Integer tsBegin, Integer tsEnd, Page page, Principal p) {
		List<String> appidParameter = new ArrayList<String>();
		appidParameter.add(app_id);
		LogSearch search = new LogSearch(database, table, appidParameter, company_id, user_id, tsBegin, tsEnd);
		
		try {
			preExcute(search, p);
		} catch (Exception e) {
			return new ElaneResponse().failure(e.getMessage());
		}
		
		List<LogEntity> list = new ArrayList<LogEntity>();
		try{
			list = service.findByPage(search, page);
		} catch (Exception e) {
			return new ElaneResponse().failure("获取数据失败");
		}
		return new ElaneResponse().success(list);
	}
	
	/**
	 * 查询日志信息
	 * @return
	 */
	@RequestMapping("/transaction")
	public ElaneResponse transaction(String app_id, String xtransaction_id, Integer tsBegin, Integer tsEnd, Page page, Principal p) {
		List<String> appidParameter = new ArrayList<String>();
		appidParameter.add(app_id);
		
		LogSearch search = new LogSearch(tsBegin, tsEnd);
		search.setAppid(appidParameter);
		search.setXtransactionid(xtransaction_id);
		
		try {
			preExcute(search, p);
		} catch (Exception e) {
			return new ElaneResponse().failure(e.getMessage());
		}
		
		List<LogEntity> list = new ArrayList<LogEntity>();
		try {
			list = service.findByPage(search, page);
		} catch (Exception e) {
			return new ElaneResponse().failure("获取数据失败");
		}
		return new ElaneResponse().success(list);
	}
	
	/**
	 * controller前处理
	 * @param search
	 * @param p
	 * @throws Exception 
	 */
	private void preExcute(LogSearch search, Principal p) throws Exception{
		List<String> appidParameter = new ArrayList<String>();
		String appid = "";
		String companyid = "";
		String role = "";
		
		OAuth2Authentication token = (OAuth2Authentication) p;
		SimpleGrantedAuthority log_api = new SimpleGrantedAuthority("OAUTH_SCOPE_logapi");
		if(!token.getAuthorities().contains(log_api)){
			throw new Exception("参数access_token包含用户信息，没有访问日志API的scope");
		}
		
		Map<String, String> tokenMap = token.getOAuth2Request().getRequestParameters();
		//暂不处理userId
		appid = tokenMap.get("client_id");
		if(tokenMap.containsKey("role")){
			companyid = tokenMap.get("companyid");
		}
		if(tokenMap.containsKey("role")){
			role = token.getOAuth2Request().getRequestParameters().get("role");
		}
		//appid 等于 日志管理id
		if("demoid".equals(appid)){
			//role 包含 admin
			if(role.contains("admin")){
				//查所有日志，直接读取参数传递关注appid和companyid
			}else{
				//可以查自己角色的所有日志,根据角色（appid_admin）获取appId
				//一个用户可能对应多个角色，设置appid为list类型
				if("".equals(role)){
					throw new Exception("参数access_token包含用户信息，没有分配任何查看日志API的角色");
				}
				
				String[] roles = role.split(",");
				for(String appidSingle : roles){
					if(appidSingle.indexOf("_admin") > -1){
						appidParameter.add(appidSingle);
					}
				}
				search.setAppid(appidParameter);
				//appid读取token，companyid读取传递参数
			}
		}else{
			//只能查自己租户的日志,不关注role的信息
			if("".equals(companyid)){
				throw new Exception("参数access_token获取用户信息，用户的companyid无效");
			}
			appidParameter.add(appid);
			search.setAppid(appidParameter);
			search.setCompanyid(companyid);
			//appid和companyid全部读取token
		}
	}
}
