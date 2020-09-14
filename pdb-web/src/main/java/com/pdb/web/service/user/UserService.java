package com.pdb.web.service.user;

import com.pdb.web.entity.bo.UserWeiXinLoginBo;

/**
 * @Description ：用户服务
 * @Tauthor ZhangZaipeng
 * @Tdata 2020/5/26   9:01
 */
public interface UserService {
  boolean registerUser(UserWeiXinLoginBo userWeiXinLoginBo);
}
