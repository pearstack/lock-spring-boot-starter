package io.github.pearstack.lock.spring.boot.autoconfigure;

import io.github.pearstack.lock.aspect.LockAspect;
import org.redisson.api.RedissonClient;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/** @author lihao3 */
@Configuration
@ConditionalOnClass(RedissonClient.class)
public class LockRedisRedissonAutoConfiguration {

  @Bean
  public LockAspect lockAspect() {
    return new LockAspect();
  }
}
