package com.template.system.domain.vo;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class LoginVO {
	@NotBlank(message = "请输入用户名")
	private String userName;
	@NotBlank(message = "请输入密码")
	private String password;
	@NotBlank(message = "请输入验证码")
	private String verify;
}
