package com.template.system.entity;

import cn.hutool.core.date.DatePattern;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * @功能：
 * @时间：2020-04-17
 * @version 1.0.0
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class SysUser implements Serializable {

	private static final long serialVersionUID = 1L;

	@TableId(value = "user_id", type = IdType.AUTO)
	private Integer userId;

	/**
	 * 用户名
	 */
	private String username;

	private String name;

	/**
	 * 密码
	 */
	private String password;
	/**
	 * 角色
	 */
	private String roles;
	/**
	 * 部门id 
	 */
	private Integer deptId;
	/**
	 * 部门名称
	 */
	private String deptName;

	/**
	 * 邮箱
	 */
	private String email;

	/**
	 * 手机号
	 */
	private String mobile;
	
	/**
	 * 逻辑删除
	 */
	@TableLogic
	private Byte deleted;

	/**
	 * 0.禁用1.正常
	 */
	private Integer status;

	@DateTimeFormat(pattern = DatePattern.NORM_DATETIME_PATTERN)
	private Date createTime;

}
