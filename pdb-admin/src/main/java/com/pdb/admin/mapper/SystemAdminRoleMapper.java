package com.pdb.admin.mapper;

import com.pdb.admin.config.mybatis.DefaultMapper;
import com.pdb.admin.entity.SystemAdminRole;

public interface SystemAdminRoleMapper extends DefaultMapper {
    int deleteByPrimaryKey(Integer roleId);

    int insert(SystemAdminRole record);

    int insertSelective(SystemAdminRole record);

    SystemAdminRole selectByPrimaryKey(Integer roleId);

    int updateByPrimaryKeySelective(SystemAdminRole record);

    int updateByPrimaryKey(SystemAdminRole record);
}
