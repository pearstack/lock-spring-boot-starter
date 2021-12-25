package io.github.pearstack.lock.service;

/**
 * 分布式锁的业务接口层
 *
 * @author lihao3
 * @date 2021/12/23 13:35
 */
public interface LockService<T> {

  /**
   * 获取锁的实现对象
   *
   * @param lockName 分布式锁名称
   * @return 分布式锁对象
   */
  T getLockObject(String lockName);

  /**
   * 上锁
   *
   * @param lockObject 锁的实现对象
   * @param expire 锁的过期时间
   * @param acquireTimeout 获取锁的超时时间
   * @return 上锁是否成功
   * @throws InterruptedException 上锁异常
   */
  Boolean onLock(T lockObject, Long expire, Long acquireTimeout) throws InterruptedException;

  /**
   * 解锁
   *
   * @param lockObject 锁的实现对象
   */
  void unLock(T lockObject);
}
