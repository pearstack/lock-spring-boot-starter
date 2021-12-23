package io.github.pearstack.lock.service;

import java.lang.reflect.Method;

/** @author lihao3 */
public interface OnLockFailedService {

  /**
   * 失败失败调用接口
   *
   * @param method 对象
   * @param arguments 异常信息
   */
  void onLockFailed(Method method, Object[] arguments);
}
