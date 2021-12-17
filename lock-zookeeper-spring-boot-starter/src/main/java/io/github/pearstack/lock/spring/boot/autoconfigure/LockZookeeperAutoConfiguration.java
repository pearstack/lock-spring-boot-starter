package io.github.pearstack.lock.spring.boot.autoconfigure;

import io.github.pearstack.lock.aspect.LockAspect;
import org.apache.curator.framework.CuratorFramework;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.zookeeper.config.CuratorFrameworkFactoryBean;
import org.springframework.integration.zookeeper.lock.ZookeeperLockRegistry;

import javax.annotation.Resource;

/**
 * @author lihao3
 * @date 2021/12/16 14:31
 */
@Configuration
@EnableConfigurationProperties(LockZookeeperAutoProperties.class)
public class LockZookeeperAutoConfiguration {

  @Resource private LockZookeeperAutoProperties lockZookeeperAutoProperties;

  @Bean
  public CuratorFrameworkFactoryBean curatorFrameworkFactoryBean() {
    return new CuratorFrameworkFactoryBean(lockZookeeperAutoProperties.getHost());
  }

  @Bean
  public ZookeeperLockRegistry zookeeperLockRegistry(CuratorFramework curatorFramework) {
    return new ZookeeperLockRegistry(curatorFramework, "/zookeeper-lock");
  }

  @Bean
  public LockAspect lockAspect() {
    return new LockAspect();
  }
}
