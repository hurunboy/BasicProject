package com.pdb.admin.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description ：
 * @Tauthor ZhangZaipeng
 * @Tdata 2020/5/24   18:12
 */
@RestController
public class TestController {
  /**
   * 没有加shiro权限注解
   * @return
   */
  @RequestMapping("/test1")
  public String test1() {
    if(SecurityUtils.getSubject().hasRole("systemUserAdd")) {
      return "有权限";
    }
    return "没有权限";
  }

  /**
   * 添加了shiro权限注解，需要用户有"systemUserAdd"权限
   * @return
   */
  @RequestMapping("/test2")
  @RequiresPermissions("systemUserAdd")
  public String test2(){
    return "test2";
  }
}
