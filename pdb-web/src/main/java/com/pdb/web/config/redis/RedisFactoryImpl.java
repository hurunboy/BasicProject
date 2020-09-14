package com.pdb.web.config.redis;

import com.pdb.common.StringUtils;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.ScanParams;
import redis.clients.jedis.ScanResult;

/**
 * 用来生成 redis 客户端的工厂类 Created by zhangzp on 2016/4/29.
 */
@Component
public class RedisFactoryImpl implements RedisFactory, InitializingBean {

  private static final String lock_prefix = "LOCK:";

  Logger logger = LoggerFactory.getLogger(RedisFactoryImpl.class);

  @Value("${spring.redis.host}")
  private String ip;
  @Value("${spring.redis.port}")
  private String port;
  @Value("${spring.redis.password}")
  private String password;

  @Value("${spring.redis.pool.min-idle}")
  private String minIdle;
  @Value("${spring.redis.pool.max-idle}")
  private String maxIdle;
  @Value("${spring.redis.pool.max-wait}")
  private String maxWaitMillis ;

  private JedisPool jedisPool;

  @Override
  public Jedis getResource() {
    if (jedisPool == null) {
      init();
    }
    return jedisPool.getResource();
  }

  private void init() {

    JedisPoolConfig config = new JedisPoolConfig();
    //最大分配对象
    //config.setMaxTotal(1024);
    config.setMaxTotal(1024);

    //最大能保持 idel状态的对象
    config.setMaxIdle(Integer.parseInt(maxIdle));

    // 连接池中的最小空闲连接
    config.setMinIdle(Integer.parseInt(minIdle));

    //当池内没有返回对象时，最大等待时间
    config.setMaxWaitMillis(Integer.parseInt(maxWaitMillis));

    //当调用borrow Object方法时，是否进行有效性检查
    config.setTestOnBorrow(false);

    //当调用return Object方法时，是否进行有效性检查
    config.setTestOnReturn(false);
    jedisPool = new JedisPool(config, ip, Integer.parseInt(port), 5000, password);
  }

  @Override
  public void afterPropertiesSet() {
    init();
  }

  @Override
  public Long ttl(String key) {
    Jedis jedis = null;
    Long value = null;
    try {
      jedis = jedisPool.getResource();
      value = jedis.ttl(key);
    } catch (Exception e) {
      logger.error(e.getMessage());
    } finally {
      jedis.close();
    }
    return value;
  }

  @Override
  public Long setExpire(String key, int seconds) {
    Jedis jedis = null;
    Long value = null;
    try {
      jedis = jedisPool.getResource();
      value = jedis.expire(key, seconds);
    } catch (Exception e) {
      logger.error(e.getMessage());
    } finally {
      jedis.close();
    }
    return value;
  }


  /**
   * <p>通过key获取储存在redis中的value</p>
   * <p>并释放连接</p>
   *
   * @param key
   *
   * @return 成功返回value 失败返回null
   */
  @Override
  public String get(String key) {
    Jedis jedis = null;
    String value = null;
    try {
      jedis = jedisPool.getResource();
      value = jedis.get(key);
    } catch (Exception e) {
      logger.error(e.getMessage());
    } finally {
      jedis.close();
    }
    return value;
  }

  /**
   * <p>向redis存入key和value,并释放连接资源</p>
   * <p>如果key已经存在 则覆盖</p>
   *
   * @param key
   * @param value
   *
   * @return 成功 返回OK 失败返回 0
   */
  @Override
  public String set(String key, String value) {
    Jedis jedis = null;
    try {
      jedis = jedisPool.getResource();
      return jedis.set(key, value);
    } catch (Exception e) {
      logger.error(e.getMessage());
      return "0";
    } finally {
      jedis.close();
    }
  }

  @Override
  public Long incr(String key,long number) {
    Jedis jedis = null;
    Long res = null;
    try {
      jedis = jedisPool.getResource();

      res = jedis.incrBy(key,number);
    } catch (Exception e) {
      logger.error(e.getMessage());
    } finally {
      jedis.close();
    }
    return res;
  }

  /**
   * <p>通过key向zset中添加value,score,其中score就是用来排序的</p>
   * <p>如果该value已经存在则根据score更新元素</p>
   *
   * @param key
   * @param score
   * @param data
   *
   * @return
   */
  @Override
  public Long zadd(String key, double score, String data) {
    Jedis jedis = null;
    Long res = null;
    try {
      jedis = jedisPool.getResource();
      return jedis.zadd(key, score, data);
    } catch (Exception e) {
      logger.error(e.getMessage());
    } finally {
      jedis.close();
    }
    return res;
  }

  /**
   * <p>通过key将获取score从start到end中zset的value</p>
   * <p>socre从大到小排序</p>
   * <p>当start为0 end为-1时返回全部</p>
   *
   * @param key
   * @param start
   * @param end
   *
   * @return
   */
  @Override
  public Set<String> zrevrange(String key, int start, int end) {
    Jedis jedis = null;
    Set<String> res = null;
    try {
      jedis = jedisPool.getResource();
      res = jedis.zrevrange(key, start, end);
    } catch (Exception e) {
      logger.error(e.getMessage());
    } finally {
      jedis.close();
    }
    return res;
  }

  @Override
  public Long decr(String key) {
    Jedis jedis = null;
    Long res = null;
    try {
      jedis = jedisPool.getResource();

      res = jedis.decr(key);
    } catch (Exception e) {
      logger.error(e.getMessage());
    } finally {
      jedis.close();
    }
    return res;
  }

  @Override
  public Set<String> keys(String pattern) {
    Jedis jedis = null;
    try {
      jedis = jedisPool.getResource();

      return jedis.keys(pattern);
    } catch (Exception e) {
      logger.error(e.getMessage());
    } finally {
      jedis.close();
    }

    return null;
  }

  @Override
  public String setex(String key, int seconds, String value) {
    Jedis jedis = null;
    String res = null;
    try {
      jedis = jedisPool.getResource();
      res = jedis.setex(key, seconds, value);
    } catch (Exception e) {
      logger.error(e.getMessage());
    } finally {
      jedis.close();
    }
    return res;
  }

  public Long hset(String key, String field, String value) {
    Jedis jedis = null;
    Long res = null;
    try {
      jedis = jedisPool.getResource();
      res = jedis.hset(key, field, value);
    } catch (Exception e) {
      logger.error(e.getMessage());
    } finally {
      jedis.close();
    }
    return res;
  }

  public String hget(String key, String field) {
    Jedis jedis = null;
    String res = null;
    try {
      jedis = jedisPool.getResource();
      res = jedis.hget(key, field);
    } catch (Exception e) {
      logger.error(e.getMessage());
    } finally {
      jedis.close();
    }
    return res;
  }

  public Long hsetnx(String key, String field, String value) {
    Jedis jedis = null;
    Long res = null;
    try {
      jedis = jedisPool.getResource();
      res = jedis.hsetnx(key, field, value);
    } catch (Exception e) {
      logger.error(e.getMessage());
    } finally {
      jedis.close();
    }
    return res;
  }

  @Override
  public String hmset(String key, Map<String, String> hash) {
    Jedis jedis = null;
    String res = null;
    try {
      jedis = jedisPool.getResource();
      res = jedis.hmset(key, hash);
    } catch (Exception e) {
      logger.error(e.getMessage());
    } finally {
      jedis.close();
    }
    return res;
  }



  public List<String> hmget(String key, String... fields) {
    Jedis jedis = null;
    List<String> res = null;
    try {
      jedis = jedisPool.getResource();
      res = jedis.hmget(key, fields);
    } catch (Exception e) {
      logger.error(e.getMessage());
    } finally {
      jedis.close();
    }
    return res;
  }

  @Override
  public Long del(String... keys) {
    Jedis jedis = null;
    String[] array = keys.clone();
    try {
      boolean flag = true;
      jedis = jedisPool.getResource();
      if (flag) {
        return jedis.del(keys);
      }
    } catch (Exception e) {
      logger.error(e.getMessage());
      return 0L;
    } finally {
      jedis.close();
    }
    return 0L;
  }

  @Override
  public ScanResult<String> sscan(String cursor, ScanParams params) {
    Jedis jedis = null;
    ScanResult<String> res = null;
    try {
      jedis = jedisPool.getResource();
      res = jedis.scan(cursor, params);
    } catch (Exception e) {
      logger.error(e.getMessage());
    } finally {
      jedis.close();
    }
    return res;
  }

  @Override
  public boolean lock(String key, long timeout, long lockExpireTime) {
    Jedis jedis = null;

    boolean lockSuccess = false;
    try {

      jedis = jedisPool.getResource();

      long start = System.currentTimeMillis();
      String lockKey = lock_prefix + key;
      do {
        long result = jedis.setnx(lockKey, String.valueOf(System.currentTimeMillis() + lockExpireTime * 1000 + 1));
        if (result == 1) { // 设置失败
          lockSuccess = true;
          break;
        } else {
          String lockTimeStr = jedis.get(lockKey);
          if (StringUtils.isNumeric(lockTimeStr)) {//如果key存在，锁存在
            long lockTime = Long.valueOf(lockTimeStr);
            if (lockTime < System.currentTimeMillis()) {//锁已过期
              String oldStr = jedis.getSet(lockKey, String.valueOf(System.currentTimeMillis() + lockExpireTime * 1000 + 1));
              if (!StringUtils.isEmpty(oldStr) && oldStr.equals(lockTimeStr)) {//表明锁由该线程获得
                lockSuccess = true;
                break;
              }
            }
          }
        }
        //如果不等待，则直接返回
        if (timeout == 0) {
          break;
        }
        //等待300ms继续加锁
        Thread.sleep(300);
      } while ((System.currentTimeMillis() - start) < timeout);

    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      jedis.close();
    }
    return lockSuccess;
  }

  @Override
  public Long unLock(String key) {
    Jedis jedis = null;

    Long ret = 0L;
    try {
      jedis = jedisPool.getResource();
      String lockKey = lock_prefix + key;
      ret = jedis.del(lockKey);
      return ret;
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      jedis.close();
    }
    return ret;
  }

  @Override
  public Long zcard(String key) {
    Jedis jedis = null;
    try{
      jedis = jedisPool.getResource();
      Long zcard = jedis.zcard(key);
      return zcard;
    } catch (Exception e) {
      e.printStackTrace();
      logger.error(e.getMessage());
    }finally {
      jedis.close();
    }
    return 0L;
  }

  @Override
  public Long zremrangeByRank(String key, int start, int end) {
    Jedis jedis = null;

    try {
      jedis = jedisPool.getResource();
      Long zremrangeByRank = jedis.zremrangeByRank(key, start, end);
      return zremrangeByRank;
    } catch (Exception e) {
      e.printStackTrace();
      logger.error(e.getMessage());
    } finally {
      jedis.close();
    }
    return 0L;
  }

  @Override
  public boolean exists(String cacheKey) {
    Jedis jedis = null;

    try {
      jedis = jedisPool.getResource();
      return jedis.exists(cacheKey);
    } catch (Exception e) {
      e.printStackTrace();
      logger.error(e.getMessage());
    } finally {
      jedis.close();
    }
    return false;
  }

  @Override
  public Long removeRangeByScore(String key, long start, long end) {
    Jedis jedis = null;

    try {
      jedis = jedisPool.getResource();
      return jedis.zremrangeByScore(key, start, end);
    } catch (Exception e) {
      e.printStackTrace();
      logger.error(e.getMessage());
    } finally {
      jedis.close();
    }
    return null;
  }
}
