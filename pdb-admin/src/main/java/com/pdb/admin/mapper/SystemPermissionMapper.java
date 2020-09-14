package com.pdb.admin.mapper;

import com.pdb.admin.config.mybatis.DefaultMapper;
import com.pdb.admin.entity.SystemPermission;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SystemPermissionMapper extends DefaultMapper {
    int deleteByPrimaryKey(Long perId);

    int insert(SystemPermission record);

    int insertSelective(SystemPermission record);

    SystemPermission selectByPrimaryKey(Long perId);

    int updateByPrimaryKeySelective(SystemPermission record);

    int updateByPrimaryKey(SystemPermission record);

    List<String> selectPermissionByUserId(long userId);

    List<String> selectAllPermission();

  List<String> selectPermissionByRoleId(@Param(value = "roleId") int roleId);
}
