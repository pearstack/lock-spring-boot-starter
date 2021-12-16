package com.pearstack.lock.spring.boot.autoconfigure;

import com.pearstack.lock.aspect.LockAspect;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.integration.redis.util.RedisLockRegistry;

/**
 * @author lihao3
 * @date 2021/12/16 14:10
 */
@Configuration
@ConditionalOnClass(RedisOperations.class)
public class LockRedisAutoConfiguration {

  @Bean
  public RedisLockRegistry redisLockRegistry(RedisConnectionFactory redisConnectionFactory) {
    return new RedisLockRegistry(redisConnectionFactory, "redis-lock");
  }

  @Bean
  public LockAspect lockAspect() {
    return new LockAspect();
  }
}
