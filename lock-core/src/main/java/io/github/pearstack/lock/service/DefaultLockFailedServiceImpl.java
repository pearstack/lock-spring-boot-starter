package io.github.pearstack.lock.service;

import io.github.pearstack.lock.exception.OnLockException;
import org.springframework.stereotype.Service;

/**
 * 默认上锁失败业务接口实现层
 *
 * @author lihao3
 */
@Service
public class DefaultLockFailedServiceImpl implements LockFailedService {

  /**
   * 上锁失败执行接口方法
   *
   * @param key 锁key
   */
  @Override
  public void onLockFailed(String key) {
    throw new OnLockException("上锁失败!");
  }
}
