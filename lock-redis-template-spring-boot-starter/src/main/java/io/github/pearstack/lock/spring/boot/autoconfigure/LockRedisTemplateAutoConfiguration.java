package io.github.pearstack.lock.spring.boot.autoconfigure;

import io.github.pearstack.lock.aspect.LockAspect;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.integration.redis.util.RedisLockRegistry;

import javax.annotation.Resource;

/**
 * @author lihao3
 */
@Configuration
@ConditionalOnClass(RedisOperations.class)
public class LockRedisTemplateAutoConfiguration {

  @Resource private LockAutoProperties properties;

  @Bean
  public RedisLockRegistry redisLockRegistry(RedisConnectionFactory redisConnectionFactory) {
    return new RedisLockRegistry(redisConnectionFactory, properties.getPrefix());
  }

  @Bean
  public LockAspect lockAspect() {
    return new LockAspect();
  }
}
