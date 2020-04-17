package com.template.system.domain.dto;

import lombok.Data;

import java.util.List;

/**
 * 
 * @功能：
 * @时间：2020-04-17
 * @version 1.0.0
 */
@Data
public class RoleDTO {

	private Integer roleId;
	private String roleName;
	private String roleSign;
	private String rights;
	private String remark;
	private List<Integer> menuIds;

}
