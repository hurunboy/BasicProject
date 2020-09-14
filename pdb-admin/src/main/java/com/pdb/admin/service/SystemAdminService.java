package com.pdb.admin.service;

import com.pdb.admin.entity.SystemAdmin;

/**
 * @Description ：
 * @Tauthor ZhangZaipeng
 * @Tdata 2020/5/24   17:40
 */
public interface SystemAdminService {

  SystemAdmin findByAdminName(String adminName);

  int findRoleIdByAdminId(Integer adminId);
}
