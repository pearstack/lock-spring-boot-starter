package com.pearstack.lock.spring.boot.autoconfigure;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import java.util.concurrent.TimeUnit;

/**
 * Lock 配置属性
 *
 * @author lihao3
 * @version 1.0.0
 */
@Getter
@Setter
@Validated
@Component
@ConfigurationProperties(prefix = "spring.lock")
public class LockAutoProperties {

  /** 锁key的前缀 */
  @NotEmpty(message = "Distributed lock prefix must not be empty!")
  private String prefix = "lock";

  /** 时间单位, 默认为毫秒 */
  private TimeUnit unit = TimeUnit.SECONDS;

  /** 锁过期时间, 默认为30秒 */
  @Min(value = 1, message = "Lock expiry time must be greater than zero")
  private long expire = 30;

  /** 获取锁超时时间, 默认为3秒 */
  @Min(value = 1, message = "Get lock timeout must be greater than zero")
  private long acquireTimeout = 3;

  /** 获取锁失败时重试间隔时间, 默认为1秒 */
  @Min(value = 1, message = "Retry interval must be greater than zero if lock acquisition fails")
  private long retryInterval = 1;
}
