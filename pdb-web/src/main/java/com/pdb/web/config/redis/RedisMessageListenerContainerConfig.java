package com.pdb.web.config.redis;

import com.pdb.web.service.TopicMessageListener;
import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
public class RedisMessageListenerContainerConfig {

  @Autowired
  private TopicMessageListener messageListener;

  @Bean
  public RedisMessageListenerContainer configRedisMessageListenerContainer(Executor executor, RedisTemplate<String,String> redisTemplate){
    RedisMessageListenerContainer container = new RedisMessageListenerContainer();
    // 设置Redis的连接工厂
    container.setConnectionFactory(redisTemplate.getConnectionFactory());
    // 设置监听使用的线程池
    container.setTaskExecutor(executor);
    // 设置监听的Topic
    ChannelTopic channelTopic = new ChannelTopic("__keyevent@0__:expired");
    // 设置监听器
    container.addMessageListener(messageListener, channelTopic);
    return container;
  }


  @Bean // 配置线程池
  public Executor redisMessageListenerPool() {
    ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
    // 核心线程池大小
    executor.setCorePoolSize(10);
    // 线程池最大容量大小
    executor.setMaxPoolSize(200);
    executor.setQueueCapacity(200);
    // 线程池空闲时，线程存活的时间
    executor.setKeepAliveSeconds(30);
    executor.setThreadNamePrefix("RedisMessageListenerPool");

    // rejection-policy：当pool已经达到max size的时候，如何处理新任务
    // CALLER_RUNS：不在新线程中执行任务，而是由调用者所在的线程来执行
    executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
    executor.initialize();
    return executor;
  }
}
