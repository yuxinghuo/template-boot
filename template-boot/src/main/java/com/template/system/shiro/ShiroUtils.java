package com.template.system.shiro;

import com.template.system.domain.dto.UserDTO;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;

/**
 * shiro utils
 * @时间：2020年4月17日
 * @version 1.0.0
 */
public class ShiroUtils {

	public static Subject getSubjct() {
		return SecurityUtils.getSubject();
	}

	public static UserDTO getUser() {
		Object object = getSubjct().getPrincipal();
		return (UserDTO) object;
	}

	public static Integer getUserId() {
		return getUser().getUserId();
	}
	
	public static String getRoles() {
		return getUser().getRoles();
	}

	public static void logout() {
		getSubjct().logout();
	}

}
