package io.github.pearstack.lock.spring.boot.autoconfigure;

import io.github.pearstack.lock.service.DefaultGetLockKeyServiceImpl;
import io.github.pearstack.lock.service.GetLockKeyService;
import io.github.pearstack.lock.service.LockRedissonServiceImpl;
import io.github.pearstack.lock.service.LockService;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * lock-redisson-spring-boot-starter 自动状态类
 *
 * @author lihao3
 */
@Configuration
@ConditionalOnClass(RedissonClient.class)
public class LockRedissonAutoConfiguration {

  @Bean
  @ConditionalOnMissingBean
  public LockService<RLock> lockService() {
    return new LockRedissonServiceImpl();
  }

  @Bean
  @ConditionalOnMissingBean
  public GetLockKeyService getLockKeyService() {
    return new DefaultGetLockKeyServiceImpl();
  }
}
