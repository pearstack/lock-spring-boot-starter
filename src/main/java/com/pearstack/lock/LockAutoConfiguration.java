package com.pearstack.lock;

import com.pearstack.lock.aspect.LockAspect;
import com.pearstack.lock.properties.LockAutoProperties;
import com.pearstack.lock.service.LockedService;
import com.pearstack.lock.service.impl.LockKeyServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.CuratorFramework;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.integration.redis.util.RedisLockRegistry;
import org.springframework.integration.zookeeper.config.CuratorFrameworkFactoryBean;
import org.springframework.integration.zookeeper.lock.ZookeeperLockRegistry;

import javax.annotation.Resource;

/**
 * Minio 自动配置
 *
 * @author lihao3
 * @version 1.0.0
 */
@Slf4j
@Configuration
@EnableConfigurationProperties(LockAutoProperties.class)
public class LockAutoConfiguration {

  @Resource private LockAutoProperties lockAutoProperties;

  @Bean
  @ConditionalOnProperty(
          value = {"spring.lock.type"},
          havingValue = "redis")
  public RedisLockRegistry redisLockRegistry(RedisConnectionFactory redisConnectionFactory) {
    return new RedisLockRegistry(redisConnectionFactory, "redis-lock");
  }

  @Bean
  @ConditionalOnProperty(
      value = {"spring.lock.type"},
      havingValue = "zookeeper")
  public CuratorFrameworkFactoryBean curatorFrameworkFactoryBean() {
    return new CuratorFrameworkFactoryBean(lockAutoProperties.getZookeeper().getHost());
  }

  @Bean
  @ConditionalOnProperty(
      value = {"spring.lock.type"},
      havingValue = "zookeeper")
  public ZookeeperLockRegistry zookeeperLockRegistry(CuratorFramework curatorFramework) {
    return new ZookeeperLockRegistry(curatorFramework, "/zookeeper-lock");
  }

  @Bean
  @ConditionalOnMissingBean
  public LockedService lockedService() {
    return new LockKeyServiceImpl();
  }

  @Bean
  @ConditionalOnMissingBean
  public LockAspect lockAspect() {
    return new LockAspect();
  }
}
