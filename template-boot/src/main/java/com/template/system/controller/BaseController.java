package com.template.system.controller;

import cn.hutool.core.collection.CollUtil;
import com.template.system.domain.dto.UserDTO;
import com.template.system.shiro.ShiroUtils;

import java.util.List;
import java.util.stream.Collectors;

public class BaseController {

	public static final String RANDOMCODEKEY = "RANDOMVALIDATECODEKEY";// 放到session中的key

	public UserDTO getUser() {
		return ShiroUtils.getUser();
	}

	public Integer getUserId() {
		return getUser().getUserId();
	}

	public String getRoles() {
		return getUser().getRoles();
	}

	public String getUsername() {
		return getUser().getUsername();
	}

	public List<Integer> removeEmpty(List<Integer> list) {
		if (CollUtil.isNotEmpty(list)) {
			return list.stream().distinct().filter(l -> l != null).collect(Collectors.toList());
		}
		return null;
	}
}