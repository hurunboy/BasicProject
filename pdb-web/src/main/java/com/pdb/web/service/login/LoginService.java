package com.pdb.web.service.login;

import com.pdb.common.platform.response.ResponseModel;

/**
 * @Description ：登录注册 服务
 * @Tauthor ZhangZaipeng
 * @Tdata 2020/5/26   10:49
 */
public interface LoginService {

  ResponseModel login(String loginType, String loginName, String code);

  /**
   * 退出登录
   * @return
   */
  ResponseModel loginOut();

  ResponseModel sendLoginMsg(String phone);


}
