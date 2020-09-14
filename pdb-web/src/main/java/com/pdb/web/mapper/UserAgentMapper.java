package com.pdb.web.mapper;

import com.pdb.web.config.mybatis.DefaultMapper;
import com.pdb.web.entity.UserAgent;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserAgentMapper extends DefaultMapper {

  int insertSelective(UserAgent record);

  UserAgent selectByPrimaryKey(Long userAgentId);

  /**
   * 根据账号获取用户
   *
   * @param loginName 账号信息
   */
  UserAgent selectByLoginName(@Param(value = "loginName") String loginName);

  /**
   * 更新登陆次数及最新登陆时间
   */
  int loginSuccess(@Param(value = "userAgentId") Long userAgentId,
      @Param(value = "lastLoginTime") String lastLoginTime);

  /**
   * 更新用户登录密码
   */
  int resetLoginPwd(@Param(value = "userId") Long userId, @Param(value = "pwd") String pwd);

  /**
   * 根据用户编号获取登录信息
   */
  UserAgent selectByUserId(@Param(value = "userId") Long userId);

  UserAgent selectByOpenId(@Param(value = "openId") String openId,
      @Param(value = "unionId") String unionId);
}
