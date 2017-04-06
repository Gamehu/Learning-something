package com.elane.core.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;

import com.elane.common.constants.ElaneConstants;
import com.elane.core.bean.ElaneUser;

/***
 * Controller 父类
 * @author hankin
 *
 */
public class BaseController {

	@Autowired
	protected HttpSession httpSession;
	

	/***
	 * 取得当前用户姓名
	 * @return
	 */
	protected String getCurrentUserName() {
		ElaneUser currentUser = getCurrentUser();
		if (null != currentUser) {
			return currentUser.getName();
		} else {
			return null;
		}
	}

	/***
	 * 取得当前登陆用户对象
	 * @return
	 */
	protected ElaneUser getCurrentUser() {
		Object object = httpSession.getAttribute(ElaneConstants.SESSION_KEY_CURRENT_USER);
		if (object instanceof ElaneUser) {
			return (ElaneUser) object;
		}
		return null;
	}
}
