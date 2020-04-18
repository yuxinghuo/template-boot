package com.template.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.template.system.entity.SysRole;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @功能：
 * @时间：2020-04-17
 * @version 1.0.0
 */
@Mapper
@Repository
public interface SysRoleMapper extends BaseMapper<SysRole> {

}
