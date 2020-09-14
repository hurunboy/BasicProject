package com.pdb.web.config;

import com.pdb.common.platform.exception.ResultErrException;
import com.pdb.common.platform.response.ResponseCode;
import com.pdb.common.platform.response.ResponseModel;
import com.pdb.web.config.login.AuthenticationException;
import com.pdb.web.config.login.LoginErrException;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

@ControllerAdvice
public class GlobalExceptionHandler {

  private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);
  private static final String URL_SUFFIX_HTML = ".htm";

  @ExceptionHandler({
      ResultErrException.class, AuthenticationException.class, LoginErrException.class
  })
  public ModelAndView resolveException(HttpServletRequest request, Exception ex) {
    String url = request.getRequestURL().toString();
    if (url.endsWith(URL_SUFFIX_HTML)) {
      ModelAndView modelAndView = new ModelAndView("/error");
      modelAndView.addObject("message", "error");
      return modelAndView;
    } else {
      ResponseModel model = ResponseModel.error(ResponseCode.API_99999).setMsg("网络不给力呀,请稍后重试!");
      if (ex instanceof ResultErrException) {
        ResultErrException resultErrException = (ResultErrException)ex;
        model = ResponseModel.getModel(resultErrException);
      } else if (ex instanceof AuthenticationException) {
        AuthenticationException authenticationException = (AuthenticationException)ex;
        model = ResponseModel.error(authenticationException.getMessage());
      } else if (ex instanceof LoginErrException) {
        model = ResponseModel.error("用户未登录");
      }

      ModelAndView modelAndView = new ModelAndView(new MappingJackson2JsonView());

      modelAndView.addAllObjects(model);

      return modelAndView;
    }
  }

  @ExceptionHandler({
      Exception.class,
  })
  public ModelAndView resolveException1(HttpServletRequest request, Exception ex) {

    // 打印堆栈日志到日志文件中
    ByteArrayOutputStream buf = new ByteArrayOutputStream();
    ex.printStackTrace(new java.io.PrintWriter(buf, true));
    String  expMessage = buf.toString();
    try {
      buf.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
    logger.error("GlobalExceptionHandler,捕获异常:"+ ex.getMessage() + "; eString:" + expMessage);

    String url = request.getRequestURL().toString();
    if (url.endsWith(URL_SUFFIX_HTML)) {
      ModelAndView modelAndView = new ModelAndView("error/error");
      modelAndView.addObject("message", "error");
      return modelAndView;
    } else {
      ResponseModel model = ResponseModel.error(ResponseCode.API_99999).setMsg("网络不给力呀,请稍后重试!");
      ModelAndView modelAndView = new ModelAndView(new MappingJackson2JsonView());
      modelAndView.addAllObjects(model);
      return modelAndView;
    }
  }
}
