package com.pdb.web.service.common;

import com.pdb.web.config.login.AuthenticationProvider;
import com.pdb.web.config.login.impl.CookieIdentityValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BCKJIdentityValidator extends CookieIdentityValidator {

  @Autowired
  private AuthenticationProvider authenticationProvider;

  @Override
  public AuthenticationProvider getAuthenticationProvider() {
    return this.authenticationProvider;
  }

  @Override
  public void setAuthenticationProvider(AuthenticationProvider authenticationProvider) {
    this.authenticationProvider = authenticationProvider;
  }

  @Override
  protected String getPrincipalcookieName() {
    return "PL_PRINCIPAL";
  }

  @Override
  protected String getImmune() {
    return null;
  }

  @Override
  protected String getvisitorCookieName() {
    return "PL_VISITOR";
  }

  @Override
  protected String getBizLastLoginTimeCookieName() {
    return "PL_BIZ_LASTLOGIN";
  }

  @Override
  protected boolean singleClientLogin() {
    return false;
  }

  @Override
  protected String getLastLoginTimeCookieName() {
    return "PL_LASTLOGIN";
  }
}
