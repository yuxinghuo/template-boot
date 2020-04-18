package com.template.system.service;



import com.baomidou.mybatisplus.extension.service.IService;
import com.template.system.domain.dto.RoleDTO;
import com.template.system.entity.SysRole;

import java.util.List;

/**
 * @功能：
 * @author： 超君子
 * @时间：2019-05-28
 * @version 1.0.0
 */
public interface SysRoleService extends IService<SysRole> {

	RoleDTO get(Integer roleId);

	List<RoleDTO> list(String roles);

	boolean save(RoleDTO role);

	boolean update(RoleDTO role);

	boolean remove(Integer roleId);

	boolean batchRemove(Integer[] roleIds);

}
