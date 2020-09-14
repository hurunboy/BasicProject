package com.pdb.web.service.login.verifier;

import com.pdb.web.config.login.KeepLoginStatusVerifier;

/**
 * @Description ：验证码登录
 * @Tauthor ZhangZaipeng
 * @Tdata 2020/6/6   10:56
 */
public class CodeVerifier extends KeepLoginStatusVerifier {
  private String loginName;
  private String code;


  public CodeVerifier(String loginName, String code) {
    this(loginName, code, true);
  }

  public CodeVerifier(String loginName, String code, boolean keepLoginStatus) {
    this(loginName, code, keepLoginStatus, DEFAULT_KEEP_LOGIN_DAY);
  }

  public CodeVerifier(String loginName, String code, boolean keepLoginStatus,
      int keepLoginDay) {
    super(keepLoginStatus, keepLoginDay);
    this.loginName = loginName;
    this.code = code;

  }

  public String getLoginName() {
    return loginName;
  }

  public String getCode() {
    return code;
  }
}
