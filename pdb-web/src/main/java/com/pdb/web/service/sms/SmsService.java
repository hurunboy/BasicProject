package com.pdb.web.service.sms;

/**
 * @Description ：
 * @Tauthor ZhangZaipeng
 * @Tdata 2020/6/7   9:47
 */
public interface SmsService {
  void sendSmsMessage(String mobile, String content);
}
