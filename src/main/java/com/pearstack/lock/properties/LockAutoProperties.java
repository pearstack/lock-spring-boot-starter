package com.pearstack.lock.properties;

import com.pearstack.lock.enums.LockTypeEnum;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import java.util.concurrent.TimeUnit;

/**
 * Lock 配置属性
 *
 * @author lihao3
 * @version 1.0.0
 */
@Validated
@Component
@ConfigurationProperties(prefix = "spring.lock")
public class LockAutoProperties {

  /** 是否开启分布式锁 */
  private Boolean isEnable;

  /** 分布式锁使用类型 */
  private LockTypeEnum type;

  /** 锁过期时间 */
  private long time;

  /** 锁过期时间单位 */
  private TimeUnit unit;

  /** 锁key的前缀 */
  private String prefix = "lock";

  /** redis属性 */
  @NestedConfigurationProperty private LockRedisProperties redis;

  /** zookeeper属性 */
  @NestedConfigurationProperty private LockZookeeperProperties zookeeper;

  public Boolean getEnable() {
    return isEnable;
  }

  public void setEnable(Boolean enable) {
    isEnable = enable;
  }

  public LockTypeEnum getType() {
    return type;
  }

  public void setType(LockTypeEnum type) {
    this.type = type;
  }

  public long getTime() {
    return time;
  }

  public void setTime(long time) {
    this.time = time;
  }

  public TimeUnit getUnit() {
    return unit;
  }

  public void setUnit(TimeUnit unit) {
    this.unit = unit;
  }

  public String getPrefix() {
    return prefix;
  }

  public void setPrefix(String prefix) {
    this.prefix = prefix;
  }

  public LockRedisProperties getRedis() {
    return redis;
  }

  public void setRedis(LockRedisProperties redis) {
    this.redis = redis;
  }

  public LockZookeeperProperties getZookeeper() {
    return zookeeper;
  }

  public void setZookeeper(LockZookeeperProperties zookeeper) {
    this.zookeeper = zookeeper;
  }
}
