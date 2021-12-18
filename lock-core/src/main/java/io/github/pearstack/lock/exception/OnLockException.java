package io.github.pearstack.lock.exception;

import java.io.Serializable;

/**
 * 加锁失败返回的异常
 *
 * @author lihao3
 */
public class OnLockException extends RuntimeException implements Serializable {

  public OnLockException(String message) {
    super(message);
  }

  public OnLockException(String message, Throwable cause) {
    super(message, cause);
  }
}
