package com.pdb.web.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Component;

/**
 * 监听 服务
 */
@Component
public class TopicMessageListener implements MessageListener {

  private Logger logger = LoggerFactory.getLogger(this.getClass());

  @Override
  public void onMessage(Message message, byte[] pattern) {
    byte[] boy = message.getBody();

    String key = new String(boy);
    logger.info(" TopicMessageListener --> itemValue:" + key);

    // TODO 多容器部署 重复处理问题 processCallback 用redis lock
    // TODO 容器在重启时 如何避免通知丢失 --> redis Set 做补偿

  }

}
