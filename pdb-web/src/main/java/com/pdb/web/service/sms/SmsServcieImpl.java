package com.pdb.web.service.sms;

import com.pdb.common.sms.SMSExecutorUtils;
import com.pdb.common.sms.TeleMessageSendThread;
import com.pdb.web.entity.SystemConfig;
import com.pdb.web.mapper.SystemConfigMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Description ：
 * @Tauthor ZhangZaipeng
 * @Tdata 2020/6/7   9:48
 */
@Service
public class SmsServcieImpl implements SmsService {

  @Autowired
  private SystemConfigMapper systemConfigMapper;

  @Override
  public void sendSmsMessage(String mobile, String content) {

    SystemConfig systemConfig = systemConfigMapper.selectByPrimaryKey("sms");

    TeleMessageSendThread teleMessageSendThread =
        new TeleMessageSendThread(mobile, content, systemConfig.getRemark1(), systemConfig.getRemark2());

    SMSExecutorUtils.executeTask(teleMessageSendThread);
  }
}
