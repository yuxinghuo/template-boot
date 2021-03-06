package com.template.system.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.crypto.digest.DigestUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.template.comon.exception.BusinessException;
import com.template.system.config.AdminConstant;
import com.template.system.domain.dto.UserDTO;
import com.template.system.domain.vo.UserVO;
import com.template.system.entity.SysRole;
import com.template.system.entity.SysUser;
import com.template.system.mapper.SysRoleMapper;
import com.template.system.mapper.SysUserMapper;
import com.template.system.service.SysUserService;
import com.template.system.util.RightsUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @功能：
 * @时间：2020-04-18
 * @version 1.0.0
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {

	@Autowired
	private SysRoleMapper sysRoleMapper;

	@Override
	public UserDTO get(Integer userId) {
		UserDTO user = BeanUtil.toBean(this.baseMapper.selectById(userId), UserDTO.class);
		if (RightsUtil.checkBigInteger(user.getRoles())) {
			LambdaQueryWrapper<SysRole> queryWrapper = Wrappers.<SysRole>lambdaQuery().select(SysRole::getRoleId);
			List<SysRole> list = sysRoleMapper.selectList(queryWrapper);
			if (CollUtil.isNotEmpty(list)) {
				user.setRoleIds(list.stream().filter(r -> RightsUtil.testRights(user.getRoles(), r.getRoleId()))
						.map(SysRole::getRoleId).collect(Collectors.toList()));
			}
		}
		if (ObjectUtil.isNull(user.getRoleIds())) {
			user.setRoleIds(Collections.emptyList());
		}
		return user;
	}

	@Override
	public int save(UserDTO user) {
		// 计算权限
		if (CollUtil.isNotEmpty(user.getRoleIds())) {
			BigInteger rights = RightsUtil.sumRights(user.getRoleIds());
			user.setRoles(rights.toString());
		}
		// 添加
		SysUser sysUser = BeanUtil.toBean(user, SysUser.class);
		int count = this.baseMapper.insert(sysUser);
		return count;
	}

	@Override
	public int update(UserDTO user) {
		// 计算权限
		if (CollUtil.isNotEmpty(user.getRoleIds())) {
			BigInteger rights = RightsUtil.sumRights(user.getRoleIds());
			user.setRoles(rights.toString());
		} else {
			user.setRoles(AdminConstant.RIGHTS_DEFAULT_VALUE);
		}
		int r = this.baseMapper.updateById(BeanUtil.toBean(user, SysUser.class));
		return r;
	}

	@Override
	public int remove(Integer userId) {
		return this.baseMapper.deleteById(userId);
	}

	@Override
	public int resetPwd(UserVO userVO, UserDTO user) {
		if ("admin".equals(user.getUsername())) {
			throw new BusinessException("超级管理员的账号不允许直接重置！");
		}
		if (Objects.equals(DigestUtil.md5Hex(user.getUsername() + userVO.getPwdOld()), user.getPassword())) {
			String password = DigestUtil.md5Hex(user.getUsername() + userVO.getPwdNew());
			user.setPassword(password);
			return this.baseMapper.updateById(BeanUtil.toBean(user, SysUser.class));
		} else {
			throw new BusinessException("输入的旧密码有误！");
		}
	}

	@Override
	public int removeBatch(Integer[] userIds) {
		int count = this.baseMapper.deleteBatchIds(CollUtil.toList(userIds));
		return count;
	}

}
