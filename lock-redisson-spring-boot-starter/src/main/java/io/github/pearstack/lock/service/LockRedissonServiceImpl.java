package io.github.pearstack.lock.service;

import io.github.pearstack.lock.spring.boot.autoconfigure.LockAutoProperties;
import org.aspectj.lang.JoinPoint;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author lihao3
 * @date 2021/12/23 13:43
 */
@Service
public class LockRedissonServiceImpl implements LockService<RLock> {

  @Resource private LockAutoProperties properties;
  @Resource private RedissonClient redissonClient;
  @Resource private GetLockKeyService getLockKeyService;

  @Override
  public RLock getLockObject(JoinPoint joinPoint, String name, String[] keys) {
    String key = getLockKeyService.getKey(joinPoint, name, keys, ":");
    return redissonClient.getLock(properties.getPrefix() + key);
  }

  @Override
  public Boolean onLock(RLock lockObject, Long expire, Long acquireTimeout)
      throws InterruptedException {
    return lockObject.tryLock(acquireTimeout, expire, properties.getUnit());
  }

  @Override
  public void unLock(RLock lockObject) {
    lockObject.unlock();
  }
}
