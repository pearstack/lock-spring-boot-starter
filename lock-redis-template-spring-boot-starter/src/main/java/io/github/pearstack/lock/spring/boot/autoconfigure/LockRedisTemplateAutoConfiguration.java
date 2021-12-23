package io.github.pearstack.lock.spring.boot.autoconfigure;

import io.github.pearstack.lock.service.LockRedisTemplateServiceImpl;
import io.github.pearstack.lock.service.LockService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.integration.redis.util.RedisLockRegistry;

import javax.annotation.Resource;
import java.util.concurrent.locks.Lock;

/**
 * lock-redis-template-spring-boot-starter 自动装配类
 *
 * @author lihao3
 */
@Configuration
@ConditionalOnClass(RedisTemplate.class)
public class LockRedisTemplateAutoConfiguration {

  @Resource private LockAutoProperties properties;

  @Bean
  public RedisLockRegistry redisLockRegistry(RedisConnectionFactory redisConnectionFactory) {
    return new RedisLockRegistry(redisConnectionFactory, properties.getPrefix());
  }

  @Bean
  @ConditionalOnMissingBean
  public LockService<Lock> lockService() {
    return new LockRedisTemplateServiceImpl();
  }
}
