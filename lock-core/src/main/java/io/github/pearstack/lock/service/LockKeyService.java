package io.github.pearstack.lock.service;

import org.aspectj.lang.JoinPoint;

/**
 * @author lihao3
 * @date 2021/12/15 12:06
 */
public interface LockKeyService {

  /**
   * 获取分布式锁的key
   *
   * @param joinPoint aop切面方法
   * @param name 对应注解的name属性
   * @param keys 对应注解的keys属性
   * @return 分布式锁
   */
  String getKey(JoinPoint joinPoint, String name, String[] keys);
}
