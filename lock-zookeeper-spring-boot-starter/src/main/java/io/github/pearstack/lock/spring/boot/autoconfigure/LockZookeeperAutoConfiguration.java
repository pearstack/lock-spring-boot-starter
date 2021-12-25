package io.github.pearstack.lock.spring.boot.autoconfigure;

import io.github.pearstack.lock.service.DefaultGetLockKeyServiceImpl;
import io.github.pearstack.lock.service.GetLockKeyService;
import io.github.pearstack.lock.service.LockService;
import io.github.pearstack.lock.service.LockZookeeperServiceImpl;
import org.apache.curator.framework.CuratorFramework;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.zookeeper.config.CuratorFrameworkFactoryBean;
import org.springframework.integration.zookeeper.lock.ZookeeperLockRegistry;

import javax.annotation.Resource;
import java.util.concurrent.locks.Lock;

/**
 * lock-zookeeper-spring-boot-starter 自动装配类
 *
 * @author lihao3
 */
@Configuration
@EnableConfigurationProperties(LockZookeeperAutoProperties.class)
public class LockZookeeperAutoConfiguration {

  @Resource private LockAutoProperties properties;
  @Resource private LockZookeeperAutoProperties lockZookeeperAutoProperties;

  @Bean
  public CuratorFrameworkFactoryBean curatorFrameworkFactoryBean() {
    return new CuratorFrameworkFactoryBean(lockZookeeperAutoProperties.getHost());
  }

  @Bean
  public ZookeeperLockRegistry zookeeperLockRegistry(CuratorFramework curatorFramework) {
    return new ZookeeperLockRegistry(curatorFramework, "/" + properties.getPrefix());
  }

  @Bean
  @ConditionalOnMissingBean
  public LockService<Lock> lockService() {
    return new LockZookeeperServiceImpl();
  }

  @Bean
  @ConditionalOnMissingBean
  public GetLockKeyService getLockKeyService() {
    return new DefaultGetLockKeyServiceImpl();
  }
}
