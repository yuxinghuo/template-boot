package com.template.system.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.template.system.domain.dto.UserDTO;
import com.template.system.domain.vo.UserVO;
import com.template.system.entity.SysUser;
import org.springframework.stereotype.Service;

/**
 * @功能：
 * @时间：2020-04-17
 * @version 1.0.0
 */
@Service
public interface SysUserService extends IService<SysUser> {

	UserDTO get(Integer userId);

	int save(UserDTO user);

	int update(UserDTO user);

	int remove(Integer userId);

	int removeBatch(Integer[] userIds);

	int resetPwd(UserVO userVO, UserDTO user);
}
