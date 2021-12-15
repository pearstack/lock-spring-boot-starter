package com.pearstack.lock.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author lihao3
 * @date 2021/12/15 11:03
 */
@Target(value = {ElementType.METHOD})
@Retention(value = RetentionPolicy.RUNTIME)
public @interface Locked {

  /**
   * 用于多个方法锁同一把锁 可以理解为锁资源名称 为空则会使用 包名+类名+方法名
   *
   * @return 名称
   */
  String name() default "";

  /**
   * support SPEL expresion 锁的key = name + keys
   *
   * @return KEY
   */
  String[] keys() default "";

  /** @return 过期时间 */
  long expire() default 0;

  /** @return 获取锁超时时间 单位：毫秒 */
  long acquireTimeout() default 0;
}
