package io.github.pearstack.lock.aspect;

import io.github.pearstack.lock.annotation.Locked;
import io.github.pearstack.lock.service.GetLockKeyService;
import io.github.pearstack.lock.service.LockService;
import io.github.pearstack.lock.service.OnLockFailedService;
import io.github.pearstack.lock.spring.boot.autoconfigure.LockAutoProperties;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * 切面实现方法
 *
 * @author lihao3
 * @date 2021/12/23 14:15
 */
@Slf4j
@Aspect
@Component
public class LockAspect {

  @Resource private LockAutoProperties properties;
  @Resource private LockService<Object> lockService;
  @Resource private GetLockKeyService lockKeyService;
  @Resource private OnLockFailedService lockFailedService;

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
    // 初始化锁对象
    Object lockObject = lockService.getLockObject(joinPoint, locked.name(), locked.keys());
    Object result = null;
    // 初始化上锁是否成功标识
    boolean lockFlag = false;
    try {
      // 尝试上锁
      lockFlag = lockService.onLock(lockObject, expire, acquireTimeout);
      // 判断是否上锁成功
      if (!lockFlag) {
        // 获取当前时间
        long start = System.currentTimeMillis();
        // 循环遍历上锁
        while (System.currentTimeMillis() - start < acquireTimeout) {
          lockFlag = lockService.onLock(lockObject, expire, acquireTimeout);
          TimeUnit.MILLISECONDS.sleep(properties.getRetryInterval());
        }
      }
      // 如果最后还是上锁失败, 那就执行异常方法
      if (!lockFlag) {
        lockFailedService.onLockFailed(
            ((MethodSignature) joinPoint.getSignature()).getMethod(), joinPoint.getArgs());
      }
      // 开始执行业务代码
      result = joinPoint.proceed();
    } catch (Throwable e) {
      lockFailedService.onLockFailed(
          ((MethodSignature) joinPoint.getSignature()).getMethod(), joinPoint.getArgs());
    } finally {
      if (lockFlag) {
        // 解锁
        lockService.unLock(lockObject);
      }
    }
    return result;
  }
}
