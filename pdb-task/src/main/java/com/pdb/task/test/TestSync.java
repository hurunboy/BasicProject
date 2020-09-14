package com.pdb.task.test;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 */
@Component
public class TestSync {

  private static final Logger logger = LoggerFactory.getLogger(TestSync.class);

  private static boolean isRunning = false;

  @Scheduled(cron = "0 0/1 * * * ?")
  public void execute() {
    if (isRunning) {
      return;
    }
    isRunning = true;

    try {
      logger.info("TestSync  execute  success ===========> ");
    } catch (Exception e) {
      logger.error("TestSync 有异常 ===========> ");
      e.printStackTrace();
    } finally {
      isRunning = false;
    }
  }

}
