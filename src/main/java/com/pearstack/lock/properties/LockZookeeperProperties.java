package com.pearstack.lock.properties;

/**
 * @author lihao3
 * @date 2021/12/15 10:21
 */
public class LockZookeeperProperties {

  /** zk 连接地址 */
  private String host;

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }
}
