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
import org.springframework.integration.redis.util.RedisLockRegistry;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;
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
  @Resource private RedisLockRegistry redisLockRegistry;
  @Resource private LockKeyService lockKeyService;
  @Resource private LockFailedService lockFailedService;

  /**
   * 注解切面地址
   *
   * @param locked 分布式锁注解
   */
  @Pointcut("@annotation(locked)")
  public void lockPointCut(Locked locked) {}

  /**
   * 实现分布式锁的核心代码
   *
   * @param joinPoint 切面方法
   * @param locked 分布式锁注解
   * @return
   */
  @Around(value = "lockPointCut(locked)", argNames = "joinPoint,locked")
  public Object around(ProceedingJoinPoint joinPoint, Locked locked) {
    // 初始化分布式锁超时时间
    long expire = locked.expire() != 0 ? locked.expire() : properties.getExpire();
    // 初始化获取锁超时时间
    long acquireTimeout =
        locked.acquireTimeout() != 0 ? locked.acquireTimeout() : properties.getAcquireTimeout();

    // 防止重试时间大于超时时间
    if (properties.getRetryInterval() >= acquireTimeout) {
      log.warn("retryInterval more than acquireTimeout,please check your configuration");
    }
    // 获取分布式锁的key
    String key = lockKeyService.getKey(joinPoint, locked.name(), locked.keys());
    // 初始化锁对象
    Lock lock = redisLockRegistry.obtain(key);
    Object result = null;
    // 初始化上锁是否成功标识
    boolean lockFlag;
    try {
      // 尝试上锁
      lockFlag = lock.tryLock(expire, properties.getUnit());
      // 判断是否上锁成功
      if (!lockFlag) {
        // 获取当前时间
        long start = System.currentTimeMillis();
        // 循环遍历上锁
        while (System.currentTimeMillis() - start < acquireTimeout) {
          lockFlag = lock.tryLock(expire, properties.getUnit());
          TimeUnit.MILLISECONDS.sleep(properties.getRetryInterval());
        }
      }
      // 如果最后还是上锁失败, 那就执行异常方法
      if (!lockFlag) {
        lockFailedService.onLockFailed(key);
      }
      // 开始执行业务代码
      result = joinPoint.proceed();
    } catch (Throwable e) {
      lockFailedService.onLockFailed(key);
    } finally {
      // 解锁
      lock.unlock();
    }
    return result;
  }
}
