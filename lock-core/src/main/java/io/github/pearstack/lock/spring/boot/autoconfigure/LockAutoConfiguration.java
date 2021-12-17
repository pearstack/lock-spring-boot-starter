package io.github.pearstack.lock.spring.boot.autoconfigure;

import io.github.pearstack.lock.service.DefaultLockFailedServiceImpl;
import io.github.pearstack.lock.service.DefaultLockKeyServiceImpl;
import io.github.pearstack.lock.service.LockFailedService;
import io.github.pearstack.lock.service.LockKeyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Lock分布式锁 自动配置类
 *
 * @author lihao3
 * @version 1.0.0
 */
@Slf4j
@Configuration
@EnableConfigurationProperties(LockAutoProperties.class)
public class LockAutoConfiguration {

  @Bean
  @ConditionalOnMissingBean
  public LockKeyService lockedService() {
    return new DefaultLockKeyServiceImpl();
  }

  @Bean
  @ConditionalOnMissingBean
  public LockFailedService lockFailedService() {
    return new DefaultLockFailedServiceImpl();
  }
}
