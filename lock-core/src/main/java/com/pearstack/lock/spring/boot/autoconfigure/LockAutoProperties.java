package com.pearstack.lock.spring.boot.autoconfigure;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
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

  /** 锁过期时间 */
  private long expire = 3;

  /** 锁过期时间单位 */
  private TimeUnit unit = TimeUnit.SECONDS;

  /** 锁key的前缀 */
  private String prefix = "lock";
}
