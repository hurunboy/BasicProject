package com.pdb.admin.controller;

import com.pdb.admin.entity.SystemAdmin;
import java.util.HashMap;
import java.util.Map;
import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * @Description ：主页
 */
@Controller
public class IndexController {
  /**
   * 首页，并将登录用户的全名返回前台
   * @return
   */
  @RequestMapping(value = {"/", "/index.htm"})
  public ModelAndView index() {
    SystemAdmin systemAdmin = (SystemAdmin) SecurityUtils.getSubject().getPrincipal();

    Map<String,Object> model = new HashMap<>();
    model.put("userName", systemAdmin.getRealName());
    model.put("headPic", systemAdmin.getHeadPic());

    return new ModelAndView("admin/index", model);
  }


  @RequestMapping(value = {"/welcome.htm"})
  public ModelAndView welcome() {
    SystemAdmin systemAdmin = (SystemAdmin) SecurityUtils.getSubject().getPrincipal();

    Map<String,Object> model = new HashMap<>();
    model.put("userName", systemAdmin.getRealName());
    model.put("headPic", systemAdmin.getHeadPic());

    return new ModelAndView("admin/welcome", model);
  }

}
