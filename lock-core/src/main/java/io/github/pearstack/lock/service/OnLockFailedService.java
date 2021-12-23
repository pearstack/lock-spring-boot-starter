package io.github.pearstack.lock.service;

/** @author lihao3 */
public interface OnLockFailedService {

  /**
   * 失败失败调用接口
   *
   * @param key 锁key
   */
  void onLockFailed(String key);

  /**
   * 失败失败调用接口
   *
   * @param key 锁key
   * @param cause 异常信息
   */
  void onLockFailed(String key, Throwable cause);
}
