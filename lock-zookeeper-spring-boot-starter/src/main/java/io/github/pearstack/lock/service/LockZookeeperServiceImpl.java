package io.github.pearstack.lock.service;

import io.github.pearstack.lock.spring.boot.autoconfigure.LockAutoProperties;
import org.aspectj.lang.JoinPoint;
import org.springframework.integration.zookeeper.lock.ZookeeperLockRegistry;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.concurrent.locks.Lock;

/**
 * lock-zookeeper业务实现层
 *
 * @author lihao3
 * @date 2021/12/23 13:43
 */
@Service
public class LockZookeeperServiceImpl implements LockService<Lock> {

  @Resource private ZookeeperLockRegistry zookeeperLockRegistry;
  @Resource private LockAutoProperties properties;
  @Resource private GetLockKeyService getLockKeyService;

  @Override
  public Lock getLockObject(JoinPoint joinPoint, String name, String[] keys) {
    String key = getLockKeyService.getKey(joinPoint, name, keys, "/");
    return zookeeperLockRegistry.obtain(key);
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
