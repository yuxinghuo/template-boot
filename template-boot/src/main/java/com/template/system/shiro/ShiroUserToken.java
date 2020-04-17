package com.template.system.shiro;

import lombok.Data;

import java.io.Serializable;

/**
 * @时间： 2020年4月17日
 * @version 1.0.0
 */
@Data
public class ShiroUserToken implements Serializable {
	private static final long serialVersionUID = 1L;
	private Long userId;
	private String username;
	private String name;
	private String password;
	private Long deptId;

}
