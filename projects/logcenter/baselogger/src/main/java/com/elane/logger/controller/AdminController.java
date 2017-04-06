package com.elane.logger.controller;

import java.security.Principal;
import java.util.List;
import java.util.Locale;

import javax.annotation.Resource;

import org.mitre.openid.connect.model.OIDCAuthenticationToken;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.elane.logger.dao.entity.Role;
import com.elane.logger.service.RoleServiceImpl;

/**
 * 后台管理界面
 */
@Controller
public class AdminController {

	@Resource
	private RoleServiceImpl roleServiceImpl;
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model, Principal p) {
		OIDCAuthenticationToken token = (OIDCAuthenticationToken) p;
		if(token != null){
			model.addAttribute("userInfo", token.getUserInfo());
		}
		return "home";
	}
	
	@RequestMapping("/user")
	@PreAuthorize("hasRole('ROLE_USER')")
	public String user(Principal p, Model model) {
		OIDCAuthenticationToken token = (OIDCAuthenticationToken) p;
		if(token != null){
			model.addAttribute("userInfo", token.getUserInfo());
			//model.addAttribute("access_token", token.getAccessTokenValue());
		}
		return "user";
	}

	@RequestMapping("/admin")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public String admin(Model model, Principal p) {
		OIDCAuthenticationToken token = (OIDCAuthenticationToken) p;
		if(token != null){
			model.addAttribute("userInfo", token.getUserInfo());
		}
		
		List<Role> roles = roleServiceImpl.list();
		model.addAttribute("roles", roles);
		return "admin";
	}
}
