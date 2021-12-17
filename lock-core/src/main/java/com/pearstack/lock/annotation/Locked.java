package com.pearstack.lock.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.concurrent.TimeUnit;

/**
 * @author lihao3
 * @date 2021/12/15 11:03
 */
@Target(value = {ElementType.METHOD})
@Retention(value = RetentionPolicy.RUNTIME)
public @interface Locked {

  /**
   * 用于多个方法锁同一把锁, 可以理解为锁资源名称 为空则会使用 锁前缀:包名.类名.方法名()
   *
   * @return 名称
   */
  String name();

  /**
   * 锁的唯一key, 支持spring el表达式
   *
   * @return KEY
   */
  String[] keys();

  /**
   * 锁过期时间
   *
   * @return 过期时间
   */
  long expire();

  /**
   * 时间单位, 默认为毫秒
   *
   * @return 锁过期时间单位
   */
  TimeUnit unit();

  /**
   * 获取锁超时时间 时间单位根据unit()变化而变化 PS: 重试时间不能大于超时时间
   *
   * @return 获取锁超时时间
   */
  long acquireTimeout();
}
