package com.pdb.admin;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by ZhangZp on 2018/9/13.
 */
@RestController
public class Zok {

  Logger logger = LoggerFactory.getLogger(Zok.class);

  @GetMapping(value = "/ok.htm")
  public String ok() {
    logger.info("ok ok ok ");
    return "ok";
  }
}
