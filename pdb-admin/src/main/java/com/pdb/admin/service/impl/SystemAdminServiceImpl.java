package com.pdb.admin.service.impl;

import com.pdb.admin.entity.SystemAdmin;
import com.pdb.admin.mapper.SystemAdminMapper;
import com.pdb.admin.service.SystemAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Description ：
 * @Tauthor ZhangZaipeng
 * @Tdata 2020/5/24   18:05
 */
@Service
public class SystemAdminServiceImpl implements SystemAdminService {

  @Autowired
  private SystemAdminMapper systemAdminMapper;

  @Override
  public SystemAdmin findByAdminName(String adminName) {
    return systemAdminMapper.findByAdminName(adminName);
  }

  @Override
  public int findRoleIdByAdminId(Integer adminId) {
    return systemAdminMapper.findRoleIdByAdminId(adminId);
  }
}
