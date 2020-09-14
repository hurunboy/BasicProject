package com.pdb.web.service.user;

import com.pdb.common.platform.Conv;
import com.pdb.common.platform.exception.ResultErrException;
import com.pdb.web.entity.User;
import com.pdb.web.entity.UserAgent;
import com.pdb.web.entity.bo.UserWeiXinLoginBo;
import com.pdb.web.mapper.UserAgentMapper;
import com.pdb.web.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Description ：
 * @Tauthor ZhangZaipeng
 * @Tdata 2020/6/6   10:44
 */
@Service
public class UserServiceImpl implements UserService{

  @Autowired
  private UserMapper userMapper;

  @Autowired
  private UserAgentMapper userAgentMapper;

  @Override
  @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
  public boolean registerUser(UserWeiXinLoginBo userWeiXinLoginBo) {
    // User
    User user = new User();
    user.setNickName(userWeiXinLoginBo.getNickName());
    user.setIconImgUrl(userWeiXinLoginBo.getAvatar());
    user.setGender(Conv.NS(userWeiXinLoginBo.getSex()));
    int r = userMapper.insertSelective(user);
    if (r <= 0) {
      throw new ResultErrException("注册失败！");
    }

    // UserAgent
    UserAgent userAgent = new UserAgent();
    userAgent.setUserId(user.getUserId());
    userAgent.setWxOpenId(userWeiXinLoginBo.getOpenId());
    userAgent.setWxUnionId(userWeiXinLoginBo.getUnionId());

    int r1 = userAgentMapper.insertSelective(userAgent);
    if (r1 <= 0) {
      throw new ResultErrException("注册失败！");
    }

    return true;
  }

}
