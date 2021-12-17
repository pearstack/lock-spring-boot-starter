package io.github.pearstack.lock.spring.boot.autoconfigure;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author lihao3
 * @date 2021/12/16 14:44
 */
@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "spring.lock.zookeeper")
public class LockZookeeperAutoProperties {

  /** zk 连接地址 */
  private String host;
}
