package com.elane.logger.controller;

import javax.annotation.Resource;

import org.elane.filter.ClientAccessTokenManger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.elane.logger.utils.ConfManger;
import com.elane.logger.utils.RestTemplateUtils;

/**
 * 请求用户中心接口
 */
@RestController
@RequestMapping("/oauth")
public class OauthController {

	@Resource
	private ClientAccessTokenManger clientAccessTokenManger;

	private final Logger logger = LoggerFactory.getLogger(OauthController.class);
	
	@RequestMapping("/userlist")
	public Object admin() {
		String url = ConfManger.getValue("uc.rolelist");
		url = url + "?access_token="+ clientAccessTokenManger.readAccessToken();
		
		String result = "";
		try {
			result = RestTemplateUtils.restRequest(url, null, null);
		} catch (Exception e) {
			logger.error("请求用户中心获取系统角色列表异常");
		}
		
		return result;
	}
}
