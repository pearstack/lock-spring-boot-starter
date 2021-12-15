package com.pearstack.lock.exception;

import java.io.Serializable;

/**
 * 加锁失败返回的异常
 *
 * @author lihao3
 * @date 2021/12/15 16:28
 */
public class OnLockException extends RuntimeException implements Serializable {


  public OnLockException(String message) {
    super(message);
  }

  public OnLockException(String message, Throwable cause) {
    super(message, cause);
  }
}
