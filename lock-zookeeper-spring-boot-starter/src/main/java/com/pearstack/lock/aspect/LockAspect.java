package com.pearstack.lock.aspect;

import com.pearstack.lock.annotation.Locked;
import com.pearstack.lock.service.LockFailedService;
import com.pearstack.lock.service.LockKeyService;
import com.pearstack.lock.spring.boot.autoconfigure.LockAutoProperties;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.integration.zookeeper.lock.ZookeeperLockRegistry;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.locks.Lock;

/**
 * @author lihao3
 * @date 2021/12/15 11:02
 */
@Slf4j
@Aspect
@Component
public class LockAspect {

  @Resource private LockAutoProperties properties;
  @Resource private ZookeeperLockRegistry zookeeperLockRegistry;
  @Resource private LockKeyService lockKeyService;
  @Resource private LockFailedService lockFailedService;

  /**
   * 注解切面地址
   *
   * @param locked 分布式锁注解
   */
  @Pointcut("@annotation(locked)")
  public void lockPointCut(Locked locked) {}

  @Around(value = "lockPointCut(locked)", argNames = "joinPoint,locked")
  public Object around(ProceedingJoinPoint joinPoint, Locked locked) throws Throwable {
    // 判断是否开启
    // 获取分布式锁的key
    String key =
        properties.getPrefix()
            + ":"
            + lockKeyService.getKey(joinPoint, locked.name(), locked.keys());
    // 初始化锁对象
    Lock lock = zookeeperLockRegistry.obtain(key);
    // 尝试上锁
    boolean lockFlag = lock.tryLock(properties.getTime(), properties.getUnit());

    Object result = null;
    try {
      result = joinPoint.proceed();
    } catch (Throwable e) {
      lockFailedService.onLockFailed(key);
    } finally {
      lock.unlock();
    }
    return result;
  }
}
