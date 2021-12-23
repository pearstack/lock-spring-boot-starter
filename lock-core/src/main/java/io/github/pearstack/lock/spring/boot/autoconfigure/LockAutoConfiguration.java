package io.github.pearstack.lock.spring.boot.autoconfigure;

import io.github.pearstack.lock.aspect.LockAspect;
import io.github.pearstack.lock.service.DefaultLockFailedServiceImpl;
import io.github.pearstack.lock.service.DefaultGetLockKeyServiceImpl;
import io.github.pearstack.lock.service.GetLockKeyService;
import io.github.pearstack.lock.service.OnLockFailedService;
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
  public GetLockKeyService lockedService() {
    return new DefaultGetLockKeyServiceImpl();
  }

  @Bean
  @ConditionalOnMissingBean
  public OnLockFailedService lockFailedService() {
    return new DefaultLockFailedServiceImpl();
  }

  @Bean
  public LockAspect lockAspect() {
    return new LockAspect();
  }
}
