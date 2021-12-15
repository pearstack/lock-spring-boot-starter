package com.pearstack.lock.aspect;

import cn.hutool.core.util.ObjectUtil;
import com.pearstack.lock.annotation.EnableLocked;
import com.pearstack.lock.annotation.Locked;
import com.pearstack.lock.enums.LockTypeEnum;
import com.pearstack.lock.properties.LockAutoProperties;
import com.pearstack.lock.service.LockedService;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.integration.redis.util.RedisLockRegistry;
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
@ConditionalOnClass({EnableLocked.class})
public class LockAspect {

  @Resource private LockAutoProperties properties;
  @Resource private RedisLockRegistry redisLockRegistry;
  @Resource private ZookeeperLockRegistry zookeeperLockRegistry;
  @Resource private LockedService lockKeyService;

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
    Lock lock = null;
    // 根据类型判读上锁机制
    if (LockTypeEnum.REDIS.equals(properties.getType())) {
      lock = redisLockRegistry.obtain(key);
    } else if (LockTypeEnum.ZOOKEEPER.equals(properties.getType())) {
      lock = zookeeperLockRegistry.obtain(key);
    }
    try {
      if (ObjectUtil.isNotEmpty(lock)) {
        boolean lockFlag = lock.tryLock(properties.getTime(), properties.getUnit());
        if (!lockFlag) {
          lockKeyService.onLockFailed(key);
        }
      }
    } catch (Exception e) {
      lockKeyService.onLockFailed(key);
    } finally {
      if (ObjectUtil.isNotEmpty(lock)) {
        lock.unlock();
      }
    }
    return joinPoint.proceed();
  }
}
