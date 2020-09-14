package com.pdb.web.config.interceptor;

import com.pdb.web.config.login.IdentityValidator;
import com.pdb.web.config.login.LoginErrException;
import com.pdb.web.config.login.Principal;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by zhangzp on 2018/9/17.
 */
@Component
public class LoginInterceptor implements HandlerInterceptor {

  Logger logger = LoggerFactory.getLogger(LoginInterceptor.class);

  @Autowired
  private IdentityValidator identityValidator;

  @Override
  public boolean preHandle(HttpServletRequest httpServletRequest,
      HttpServletResponse httpServletResponse, Object o) throws Exception {
    Principal principal = identityValidator.currentPrincipal();

    if (principal == null) { // 未登录
      throw new LoginErrException();
    }

    return true;
  }

  @Override
  public void postHandle(HttpServletRequest httpServletRequest,
      HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView)
      throws Exception {

  }

  @Override
  public void afterCompletion(HttpServletRequest httpServletRequest,
      HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

  }
}
