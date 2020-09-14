package com.pdb.admin.mapper;

import com.pdb.admin.config.mybatis.DefaultMapper;
import com.pdb.admin.entity.SystemAdmin;
import org.apache.ibatis.annotations.Param;

public interface SystemAdminMapper extends DefaultMapper {

  int deleteByPrimaryKey(Integer adminId);

  int insert(SystemAdmin record);

  int insertSelective(SystemAdmin record);

  SystemAdmin selectByPrimaryKey(Integer adminId);

  int updateByPrimaryKeySelective(SystemAdmin record);

  int updateByPrimaryKey(SystemAdmin record);

  SystemAdmin findByAdminName(@Param(value = "adminName") String adminName);

  int findRoleIdByAdminId(@Param(value = "adminId") int adminId);
}
