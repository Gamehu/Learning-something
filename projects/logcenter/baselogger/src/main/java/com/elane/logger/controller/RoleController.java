package com.elane.logger.controller;

import javax.annotation.Resource;

import org.elane.filter.ClientAccessTokenManger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.elane.logger.dao.entity.Role;
import com.elane.logger.service.RoleServiceImpl;

/**
 * 角色管理
 */
@Controller
@RequestMapping("/role")
public class RoleController {

	private final Logger logger = LoggerFactory.getLogger(RoleController.class);
	
	@Resource
	private RoleServiceImpl roleServiceImpl;
	
	@Resource
	private ClientAccessTokenManger clientAccessTokenManger;
	
	@RequestMapping(value="/list")
	@ResponseBody
	public Object list() {
		return roleServiceImpl.list();
	}
	
	@RequestMapping(value="/save")
	public String save(String role, String remark) {
		int result = 0;
		try {
			result = roleServiceImpl.save(new Role(role, remark), preExcute());
		} catch (Exception e) {
			logger.error("保存异常--角色--" + role);
			logger.error(e.getMessage());
		}
		if(result > 0){
			logger.info("保存成功--角色--" + role);
		}else{
			logger.info("保存失败--角色--" + role);
		}
		
		return "redirect:/admin";
	}
	
	@RequestMapping(value="/delete")
	public String delete(String role) {
		try {
			roleServiceImpl.delete(role, preExcute());
		} catch (Exception e) {
			logger.error("删除异常--角色--" + role);
			logger.error(e.getMessage());
		}
		return "redirect:/admin";
	}
	
	/**
	 * 预处理获取access_token
	 * @return
	 * @throws Exception
	 */
	private String preExcute() throws Exception{
		String access_token = clientAccessTokenManger.readAccessToken();
		if(access_token == null){
			throw new Exception("获取access_token失败");
		}
		return access_token;
	}
}
