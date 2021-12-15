package com.pearstack.lock.enums;

/**
 * 分布式锁使用类型
 *
 * @author lihao3
 * @date 2021/12/15 10:11
 */
public enum LockTypeEnum {
  /** redis模式 */
  REDIS,

  /** zookeeper模式 */
  ZOOKEEPER
}

