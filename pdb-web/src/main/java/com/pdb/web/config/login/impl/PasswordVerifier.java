/**
 * @(#)PasswordVerifier.java Copyright 2011 jointown, Inc. All rights reserved.
 */
package com.pdb.web.config.login.impl;

import com.pdb.web.config.login.KeepLoginStatusVerifier;

/**
 * 密码认证校验器
 *
 * @author jianguo.xu
 * @version 1.0, 2011-2-17
 */
public class PasswordVerifier extends KeepLoginStatusVerifier {

  private String loginName;
  private String password;

  public PasswordVerifier(String loginName, String password) {
    this(loginName, password, false);
  }

  public PasswordVerifier(String loginName, String password, boolean keepLoginStatus) {
    this(loginName, password, keepLoginStatus, DEFAULT_KEEP_LOGIN_DAY);
  }

  public PasswordVerifier(String loginName, String password, boolean keepLoginStatus,
      int keepLoginDay) {
    super(keepLoginStatus, keepLoginDay);
    this.loginName = loginName;
    this.password = password;

  }

  public String getPassword() {
    return password;
  }

  public String getLoginName() {
    return loginName;
  }

}
