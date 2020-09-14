package com.pdb.admin.config.shiro;

import com.pdb.common.crypt.BCrypt;
import com.pdb.common.platform.Conv;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.CredentialsMatcher;

public class BCCredentialsMatcher implements CredentialsMatcher {

  @Override
  public boolean doCredentialsMatch(AuthenticationToken authcToken, AuthenticationInfo info) {
    UsernamePasswordToken token = (UsernamePasswordToken) authcToken;
    String loginPwd = new String(token.getPassword());

    Object password = info.getCredentials();

    if (!BCrypt.checkpw(loginPwd, Conv.NS(password))) {
      throw new IncorrectCredentialsException();
    }

    return true;
  }

}
