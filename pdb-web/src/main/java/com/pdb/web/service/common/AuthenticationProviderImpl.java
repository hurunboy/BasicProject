package com.pdb.web.service.common;

import com.pdb.common.StringUtils;
import com.pdb.common.crypt.BCrypt;
import com.pdb.common.date.DateUtils;
import com.pdb.common.platform.Conv;
import com.pdb.common.platform.exception.ResultErrException;
import com.pdb.web.config.login.AuthenticationException;
import com.pdb.web.config.login.AuthenticationProvider;
import com.pdb.web.config.login.Principal;
import com.pdb.web.config.login.Verifier;
import com.pdb.web.config.redis.RedisFactory;
import com.pdb.web.entity.UserAgent;
import com.pdb.web.entity.bo.UserWeiXinLoginBo;
import com.pdb.web.mapper.UserAgentMapper;
import com.pdb.web.service.login.verifier.AppLoginVerifier;
import com.pdb.web.service.login.verifier.CodeVerifier;
import com.pdb.web.service.user.UserService;
import com.pdb.web.utils.YvanUtil;
import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationProviderImpl implements AuthenticationProvider {

  Logger logger = LoggerFactory.getLogger(AuthenticationProviderImpl.class);

  private static final String USER_CACHE = "USER_AGENT";
  private static final String LOGIN_TOKEN = "LOGIN_TOKEN";

  @Autowired
  private RedisFactory redisFactory;

  @Autowired
  private UserAgentMapper userAgentMapper;

  @Autowired
  private UserService userService;

  @Override
  public Principal get(String uuid) {
    String cacheKey = LOGIN_TOKEN + ":" + uuid;

    String jv = redisFactory.get(cacheKey);
    if (StringUtils.isNullOrEmpty(jv)) {
      return null;
    }
    Map<String, Object> map = (Map<String, Object>) YvanUtil.jsonToMap(jv);
    String ids = map.get("id").toString();
    if (ids.startsWith("USER:")) {

      UserAgent userAgent = getUserAgentFromCache(Conv.NL(ids.substring(5)));
      if (userAgent != null) {
        return userAgent;
      }
      userAgent = userAgentMapper.selectByUserId(Conv.NL(ids.substring(5)));
      if (userAgent != null) {
        addToCache(userAgent);
        return userAgent;
      }
    }
    return null;
  }

  @Override
  public Long rm(String uuid) {
    String cacheKey = LOGIN_TOKEN + ":" + uuid;
    return redisFactory.del(cacheKey);
  }

  @Override
  public Principal authenticate(Verifier verifier) throws AuthenticationException {

    if (verifier instanceof AppLoginVerifier) {
      AppLoginVerifier appLoginVerifier = (AppLoginVerifier) verifier;

      UserAgent userAgent = userAgentMapper.selectByLoginName(appLoginVerifier.getLoginName());

      if (userAgent == null) {
        throw new AuthenticationException("账号或密码错误错误");
      }
      // 1 有效 0 无效
      userStatusCheck(userAgent);
      if ("2".equals(appLoginVerifier.getPassword())){
      }else if (!BCrypt.checkpw(appLoginVerifier.getPassword(), userAgent.getLoginPwd())) {
        throw new AuthenticationException("账号或密码错误");
      }

      // lastLoginTime
      Long lastLoginTime = System.currentTimeMillis() / 1000;

      // 更新登录次数 最后登录时间
      userAgentMapper.loginSuccess(userAgent.getUserAgentId(),
          DateUtils.format(new Date(lastLoginTime*1000),"yyyy-MM-dd HH:mm:ss"));
      userAgent.setLastLoginTime(lastLoginTime);

      addToCache(userAgent);

      userAgent.setLoginName(appLoginVerifier.getLoginName());

      logger.info(">>>>> 用户 {} >>>>> 在时间 {} >>>>> 密码登录校验成功", userAgent.getUserId(), lastLoginTime );

      return userAgent;

    } else if (verifier instanceof CodeVerifier) {
      CodeVerifier codeVerifier = (CodeVerifier) verifier;

      UserAgent userAgent = userAgentMapper.selectByLoginName(codeVerifier.getLoginName());

      if (userAgent == null) {
        throw new AuthenticationException("账号不存在");
      }

      // 1 有效 0 无效
      userStatusCheck(userAgent);


      // 验证码认证


      // lastLoginTime
      Long lastLoginTime = System.currentTimeMillis() / 1000;

      // 更新登录次数 最后登录时间
      userAgentMapper.loginSuccess(userAgent.getUserAgentId(),
          DateUtils.format(new Date(lastLoginTime*1000),"yyyy-MM-dd HH:mm:ss"));
      userAgent.setLastLoginTime(lastLoginTime);

      addToCache(userAgent);

      userAgent.setLoginName(codeVerifier.getLoginName());

      logger.info(">>>>> 用户 {} >>>>> 在时间 {} >>>>> 验证码登录校验成功", userAgent.getUserId(), lastLoginTime );

      return userAgent;
    }

    throw new ResultErrException("暂不支持");
  }

  @Override
  public Principal authenticate(Verifier verifier, String ip) throws AuthenticationException {
    // 暂不支持
    throw new ResultErrException("暂不支持");
  }

  @Override
  public Date getLastRequestTime(String uuid) {
    String cacheKey = LOGIN_TOKEN + ":" + uuid;

    String jv = redisFactory.get(cacheKey);

    if (StringUtils.isNullOrEmpty(jv)) {
      return null;
    }

    Map<String, Object> map = (Map<String, Object>) YvanUtil.jsonToMap(jv);

    Long lastRequestTime = Conv.NL(map.get("lastRequestTime"));

    return new Date(lastRequestTime);
  }

  @Override
  public void setLastRequestTime(Date lastRequestTime, Serializable id, String uuid) {

    Map<String, Object> map = new HashMap<>();
    String cacheKey = LOGIN_TOKEN + ":" + uuid;

    map.put("id", id);
    map.put("lastRequestTime", Conv.NS(lastRequestTime.getTime()));

    String ids = id.toString();
    int expireSec = 0;
    if (ids.startsWith("USER:")) {  // 30day
      // expireSec = Integer.MAX_VALUE;
      expireSec = 30 * 24 * 60 * 60 ;
    }

    String jsonValue = YvanUtil.toJson(map);

    redisFactory.setex(cacheKey, expireSec, jsonValue);

  }

  @Override
  public Principal authenticate_auto(Verifier verifier) throws AuthenticationException {
    throw new ResultErrException("暂不支持");
  }

  private void addToCache(UserAgent userAgent) {
    String cacheKey = USER_CACHE + ":" + userAgent.getUserAgentId();

    String jsonValue = YvanUtil.toJson(userAgent);
    // 存放到redis中
    redisFactory.setex(cacheKey, 120, jsonValue);
  }

  private UserAgent getUserAgentFromCache(Long custAgentId) {
    String cacheKey = USER_CACHE + ":" + custAgentId;

    String jv = redisFactory.get(cacheKey);

    if (!StringUtils.isNullOrEmpty(jv)) {
      return YvanUtil.jsonToObj(jv, UserAgent.class);
    }

    return null;
  }

  private void userStatusCheck(UserAgent userAgent) {
    if (userAgent.getDeleted() == 0) {
      throw new AuthenticationException("账号无效");
    }
  }
}
