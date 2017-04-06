package com.elane.logger.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.elane.logger.dao.RoleDao;
import com.elane.logger.dao.entity.Role;
import com.elane.logger.utils.ConfManger;
import com.elane.logger.utils.RestTemplateUtils;
import com.nimbusds.jose.util.JSONObjectUtils;

@Service
public class RoleServiceImpl {
	
	private final Logger logger = LoggerFactory.getLogger(RoleServiceImpl.class);
	
    @Autowired
    private RoleDao roleDao;
    
	public List<Role> list() {
		List<Role> list = roleDao.select(new Role());
		return list;
	}
	
	public int save(Role role, String access_token) throws Exception {
		//调用用户中心接口添加角色
		String url = ConfManger.getValue("uc.roleadd");
		url = url + "?access_token="+ access_token;
		
		Map<String, String> parameter = new HashMap<String, String>();
		parameter.put("role", role.getRole());
		parameter.put("mark", role.getRemark());
		
		String apiResult = RestTemplateUtils.restRequest(url, parameter, null);//返回结果
		int apiStatus = JSONObjectUtils.getInt(JSONObjectUtils.parseJSONObject(apiResult), "status");
		String apiMsg = JSONObjectUtils.getString(JSONObjectUtils.parseJSONObject(apiResult), "msg");
		if(apiStatus != 1){
			throw new Exception(apiMsg);
		}
		//插入数据库
		int result = roleDao.insert(role);
		return result;
	}
	
	public int delete(String role, String access_token) throws Exception {
		int count = 0;
		String[] roles = role.split(",");
		for(String str : roles){
			
			//调用用户中心接口删除角色
			String url = ConfManger.getValue("uc.roleremove");
			url = url + "?access_token="+ access_token;
			
			Map<String, String> parameter = new HashMap<String, String>();
			parameter.put("role", str);
			
			String apiResult = RestTemplateUtils.restRequest(url, parameter, null);//返回结果
			int apiStatus = JSONObjectUtils.getInt(JSONObjectUtils.parseJSONObject(apiResult), "status");
			String apiMsg = JSONObjectUtils.getString(JSONObjectUtils.parseJSONObject(apiResult), "msg");
			if(apiStatus != 1){
				throw new Exception(apiMsg);
			}
			
			int result = roleDao.delete(new Role(str));
			if(result > 0){
				logger.info("删除成功--角色" + role);
			}else{
				logger.info("删除失败--角色" + role);
			}
			count = count + result;
		}
		return count;
	}
}
