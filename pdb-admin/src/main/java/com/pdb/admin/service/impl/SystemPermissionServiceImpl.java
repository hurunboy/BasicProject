package com.pdb.admin.service.impl;

import com.pdb.admin.mapper.SystemPermissionMapper;
import com.pdb.admin.service.SystemPermissionService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Description ：
 * @Tauthor ZhangZaipeng
 * @Tdata 2020/5/24   17:41
 */
@Service
public class SystemPermissionServiceImpl implements SystemPermissionService {

  @Autowired
  private SystemPermissionMapper systemPermissionMapper;

  public List<String> selectAllPermission() {
    return systemPermissionMapper.selectAllPermission();
  }

  public List<String> selectPermissionByUserId(long userId) {
    return systemPermissionMapper.selectPermissionByUserId(userId);
  }

  @Override
  public List<String> selectPermissionByRoleId(int roleId) {
    return systemPermissionMapper.selectPermissionByRoleId(roleId);
  }

}
