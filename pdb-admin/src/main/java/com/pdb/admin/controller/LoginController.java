package com.pdb.admin.controller;

import com.pdb.common.platform.springmvc.HttpParameterParser;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.DisabledAccountException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description ：登录注册 接口
 */
@Controller
public class LoginController {
  /**
   * get请求，登录页面跳转
   * @return
   */
  @GetMapping("/login.htm")
  public ModelAndView login() {
    //如果已经认证通过，直接跳转到首页
    if (SecurityUtils.getSubject().isAuthenticated()) {
      return new ModelAndView("redirect:/index.htm");
    }

    return new ModelAndView("admin/login");
  }

  /**
   * post表单提交，登录
   * @return
   */
  @PostMapping("/login.htm")
  public ModelAndView login(HttpServletRequest request) {
    Subject user = SecurityUtils.getSubject();
    HttpParameterParser httpParameterParser = HttpParameterParser.newInstance(request);
    String loginName = httpParameterParser.getString("loginName");
    String password = httpParameterParser.getString("password");

    UsernamePasswordToken token = new UsernamePasswordToken(loginName, password);
    Map<String,Object> model = new HashMap<>();
    try {
      //shiro帮我们匹配密码什么的，我们只需要把东西传给它，它会根据我们在UserRealm里认证方法设置的来验证
      user.login(token);
      return new ModelAndView("redirect:/index.htm");
    } catch (UnknownAccountException | IncorrectCredentialsException e) {
      //账号不存在和下面密码错误一般都合并为一个账号或密码错误，这样可以增加暴力破解难度
      model.put("message", "账号或密码错误！");
    } catch (DisabledAccountException e) {
      model.put("message", "账号未启用！");
    } catch (Throwable e) {
      model.put("message", "未知错误！");
    }

    model.put("loginName", loginName);

    return new ModelAndView("admin/login", model);
  }

  /**
   * 退出
   * @return
   */
  @RequestMapping("/logout.htm")
  public ModelAndView logout() {
    SecurityUtils.getSubject().logout();
    return new ModelAndView("redirect:/login.htm");
  }
}
