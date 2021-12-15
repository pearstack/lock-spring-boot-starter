package com.pearstack.lock.properties;

import com.pearstack.lock.enums.LockTypeEnum;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * Lock 配置属性
 *
 * @author lihao3
 * @version 1.0.0
 */
@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "spring.lock")
public class LockAutoProperties {

  /** 分布式锁使用类型 */
  private LockTypeEnum type = LockTypeEnum.REDIS;

  /** 锁过期时间 */
  private long time = 3;

  /** 锁过期时间单位 */
  private TimeUnit unit = TimeUnit.SECONDS;

  /** 锁key的前缀 */
  private String prefix = "lock";

  /** zookeeper属性 */
  @NestedConfigurationProperty private LockZookeeperProperties zookeeper;
}
