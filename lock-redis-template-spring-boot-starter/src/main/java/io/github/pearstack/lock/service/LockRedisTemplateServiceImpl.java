package io.github.pearstack.lock.service;

import io.github.pearstack.lock.spring.boot.autoconfigure.LockAutoProperties;
import org.springframework.integration.redis.util.RedisLockRegistry;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.concurrent.locks.Lock;

/**
 * redis-template业务实现层
 *
 * @author lihao3
 * @date 2021/12/23 13:43
 */
@Service
public class LockRedisTemplateServiceImpl implements LockService<Lock> {

  @Resource private LockAutoProperties properties;
  @Resource private RedisLockRegistry redisLockRegistry;

  @Override
  public Lock getLockObject(String key) {
    return redisLockRegistry.obtain(key);
  }

  @Override
  public Boolean onLock(Lock lockObject, Long expire, Long acquireTimeout)
      throws InterruptedException {
    return lockObject.tryLock(expire, properties.getUnit());
  }

  @Override
  public void unLock(Lock lockObject) {
    lockObject.unlock();
  }
}
