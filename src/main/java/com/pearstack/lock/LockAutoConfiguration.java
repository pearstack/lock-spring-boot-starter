package com.pearstack.lock;

import com.pearstack.lock.annotation.EnableLocked;
import com.pearstack.lock.properties.LockAutoProperties;
import org.apache.curator.framework.CuratorFramework;
import org.apache.zookeeper.client.ZooKeeperSaslClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
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
@Configuration
@ConditionalOnClass({EnableLocked.class})
@EnableConfigurationProperties(LockAutoProperties.class)
public class LockAutoConfiguration {

  private static final Logger log = LoggerFactory.getLogger(LockAutoConfiguration.class);

  @Resource private LockAutoProperties lockAutoProperties;

  @Bean
  @ConditionalOnProperty(
      value = {"spring.lock.type"},
      havingValue = "0")
  @ConditionalOnClass(StringRedisTemplate.class)
  public RedisLockRegistry redisLockRegistry(RedisConnectionFactory redisConnectionFactory) {
    return new RedisLockRegistry(redisConnectionFactory, "redis-lock");
  }

  @Bean
  @ConditionalOnClass(ZooKeeperSaslClient.class)
  public CuratorFrameworkFactoryBean curatorFrameworkFactoryBean() {
    return new CuratorFrameworkFactoryBean(lockAutoProperties.getZookeeper().getHost());
  }

  @Bean
  @ConditionalOnClass(ZooKeeperSaslClient.class)
  public ZookeeperLockRegistry zookeeperLockRegistry(CuratorFramework curatorFramework) {
    return new ZookeeperLockRegistry(curatorFramework, "/zookeeper-lock");
  }
}
