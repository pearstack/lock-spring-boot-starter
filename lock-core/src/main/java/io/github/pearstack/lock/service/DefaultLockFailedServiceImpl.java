package io.github.pearstack.lock.service;

import io.github.pearstack.lock.exception.OnLockException;
import org.springframework.stereotype.Service;

/**
 * @author lihao3
 * @date 2021/12/16 14:19
 */
@Service
public class DefaultLockFailedServiceImpl implements LockFailedService {

  /**
   * 失败失败调用接口
   *
   * @param key 锁key
   */
  @Override
  public void onLockFailed(String key) {
    throw new OnLockException("上锁失败!");
  }
}
