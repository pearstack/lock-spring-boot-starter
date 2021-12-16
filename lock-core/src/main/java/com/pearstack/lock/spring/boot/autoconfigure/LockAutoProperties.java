package com.pearstack.lock.spring.boot.autoconfigure;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

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

  /** 锁key的前缀 */
  private String prefix = "lock";
}
