package com.template.system.service;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.extension.service.IService;
import com.template.system.model.MenuAdmin;
import com.template.system.model.MenuLayuiDTO;
import com.template.system.entity.SysMenu;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Set;

/**
 * @功能：
 * @时间：2020-04-17
 * @version 1.0.0
 */
@Service
public interface SysMenuService extends IService<SysMenu> {

	List<Integer> SHOW_MENU_TYPE = CollUtil.toList(0, 1);// 1/2 级菜单

	/**
	 * 获取用户权限
	 * @param userId
	 * @return
	 */
	Set<String> listPerms(String roles);

	/**
	 * 获取用户左侧菜单
	 * @param userId
	 * @return
	 */
	List<MenuLayuiDTO> listMenuLayuiTree(String roles);

	/**
	 * 递归删除
	 * @param id
	 * @return
	 */
	boolean recursionRemove(@NotNull Integer id);

	/**
	 * 批量添加
	 * @param root
	 * @param menus
	 * @return
	 */
	boolean adminSaveBatch(MenuAdmin root, List<MenuAdmin> menus);
}
