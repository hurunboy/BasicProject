package com.pdb.admin.service;

import java.util.List;

public interface SystemPermissionService {

  List<String> selectAllPermission();

  List<String> selectPermissionByUserId(long userId);

  List<String> selectPermissionByRoleId(int roleId);
}
