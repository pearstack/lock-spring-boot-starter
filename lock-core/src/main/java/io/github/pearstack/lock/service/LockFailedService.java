package io.github.pearstack.lock.service;

/**
 * @author lihao3
 */
public interface LockFailedService {

  /**
   * 失败失败调用接口
   *
   * @param key 锁key
   */
  void onLockFailed(String key);
}
