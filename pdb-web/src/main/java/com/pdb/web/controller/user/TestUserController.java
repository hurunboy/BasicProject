package com.pdb.web.controller.user;

import com.pdb.common.platform.response.ResponseModel;
import com.pdb.common.platform.springmvc.HttpParameterParser;
import com.pdb.web.mapper.UserMapper;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 */
@Controller
@RequestMapping("/user")
public class TestUserController {

  @Autowired
  private UserMapper userMapper;

  @GetMapping("/userinfo.json")
  public ResponseModel userInfo(HttpServletRequest request) {
    HttpParameterParser httpParameterParser = HttpParameterParser.newInstance(request);

    Long userId = httpParameterParser.getLong("userId");

    return ResponseModel.ok(userMapper.selectByPrimaryKey(userId));
  }

}
