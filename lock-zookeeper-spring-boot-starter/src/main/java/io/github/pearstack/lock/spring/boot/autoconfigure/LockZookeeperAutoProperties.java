package io.github.pearstack.lock.spring.boot.autoconfigure;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotEmpty;

/** @author lihao3 */
@Getter
@Setter
@Validated
@Component
@ConfigurationProperties(prefix = "spring.lock.zookeeper")
public class LockZookeeperAutoProperties {

  /** zk 连接地址 */
  @NotEmpty(message = "zookeeper连接地址不可为空!")
  private String host;
}
