package com.pdb.common.sms;

import com.pdb.common.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 发送短信线程
 */
public class TeleMessageSendThread implements Runnable {

  private final Logger logger = LoggerFactory.getLogger(TeleMessageSendThread.class);

  private String mobile;
  private String content;
  private String uname;
  private String pwd;

  public TeleMessageSendThread(String mobile, String content, String uname, String pwd){
    this.mobile = mobile;
    this.content = content;
    this.uname = uname;
    this.pwd = pwd;
  }

  @Override
  public void run() {
    if (!StringUtils.isNullOrEmpty(mobile)) {
      // 发送短信 一共尝试2次
      boolean sendMsgFlag = false;
      for (int i = 0; i < 3; i++) {
        sendMsgFlag = HBSmsUtil.sendMessage(mobile, content, uname, pwd);
        if (sendMsgFlag) {
          break;
        }
      }
    } else {
      logger.error("TeleMessageSendThread : 用户异常：" , mobile);
    }
  }
}
