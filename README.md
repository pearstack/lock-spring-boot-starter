<h1 align="center">
      lock-spring-boot-starter
</h1>

<h4 align="center">
A distributed lock that supports the use of Redis and Zookeeper, out of the box, fast and easy to use

<h4 align="center">
    一款基于 Redis 和 Zookeeper 的分布式锁, 开箱即用，快速且易于使用
</h4> 
<p align="center">
    <a href="https://github.com/pearstack/lock-spring-boot-starter/issues"><img alt="GitHub issues" src="https://img.shields.io/github/issues/pearstack/lock-spring-boot-starter"></a>
    <a href="https://github.com/pearstack/lock-spring-boot-starter/network"><img alt="GitHub forks" src="https://img.shields.io/github/forks/pearstack/lock-spring-boot-starter"></a>
    <a href="https://github.com/pearstack/lock-spring-boot-starter/stargazers"><img alt="GitHub stars" src="https://img.shields.io/github/stars/pearstack/lock-spring-boot-starter"></a>
    <a href="https://github.com/pearstack/lock-spring-boot-starter/blob/master/LICENSE"><img alt="GitHub license" src="https://img.shields.io/github/license/pearstack/lock-spring-boot-starter"></a>
</p>
<p align="center">
    <a href="https://github.com/spring-projects/spring-boot"><img alt="spring boot version" src="https://img.shields.io/badge/spring--boot-2.4.1-brightgreen"></a>
    <a href="https://github.com/redisson/redisson"><img alt="redisson version" src="https://img.shields.io/badge/redisson-3.16.6-brightgreen"></a>
    <a href="https://github.com/dromara/hutool"><img alt="hutool version" src="https://img.shields.io/badge/hutool--core-5.7.17-brightgreen"></a>




# 工程简介

1. 简单描述

   `lock-spring-boot-starter` 一款分布式锁, 支持使用 `Redis` 和 `ZooKeeper`, 开箱即用, 快捷方便

2. 特点

   1. `spring-boot-starter`组件开箱即用, 快捷方便
   2. 支持`自定义key`, 以及`spring el`表达式
   3. 将生成key的方法和上锁失败的异常方法作为接口, 可以自定义实现层, 更加自由

3. [框架所用技术](https://github.com/pearstack/lock-spring-boot-starter/network/dependencies)

# 使用方法

1. 引入jar包(**PS:三种模式只能选择一种**)

   1. Redis Template模式

      ```xml
          <dependency>
              <groupId>io.github.pearstack</groupId>
              <artifactId>lock-redis-template-spring-boot-starter</artifactId>
              <version>0.0.3</version>
          </dependency>
      ```

      

   2. Redisson 模式

      ```xml
          <dependency>
              <groupId>io.github.pearstack</groupId>
              <artifactId>lock-redisson-spring-boot-starter</artifactId>
              <version>0.0.3</version>
          </dependency>
      ```

      

   3. ZooKeeper 模式

      ```xml
          <dependency>
              <groupId>io.github.pearstack</groupId>
              <artifactId>lock-zookeeper-spring-boot-starter</artifactId>
          	<version>0.0.3</version>
          </dependency>
      ```

2. 全局配置文件

   ```yaml
   spring:
     lock:
       # 锁key的前缀, 默认为lock, 可不设置
       prefix: lock
       # 锁过期时间, 默认为30秒, 可不设置
       expire: 30
       # 获取锁超时时间, 默认为3秒, 可不设置
       acquire-timeout: 3
       # 获取锁失败时重试间隔时间, 默认为1秒, 可不设置
       retry-interval: 1
       # 时间单位, 默认为秒, 可不设置
       unit: seconds
       # ZooKeeper 模式
       zookeeper:
         host: xxx.xxx.xxx.xxx
   # Redis 模式
     redis:
       host: xxx.xxx.xxx.xxx
   ```

   

3. 在需要使用分布式锁的地方添加对应注解

   ```java
   package com.lihao.lock.controller;
   
   import io.github.pearstack.lock.annotation.Locked;
   import lombok.extern.slf4j.Slf4j;
   import org.springframework.web.bind.annotation.GetMapping;
   import org.springframework.web.bind.annotation.RestController;
   
   @Slf4j
   @RestController
   public class TestController {
   
     public static Integer apple = 200;
     public static Integer pear = 200;
   
     /**
      * 带分布式锁, 不会出现超卖
      *
      * @param appleId
      * @return
      */
     @Locked(keys = "#appleId")
     @GetMapping("/get/apple")
     public String getApple(Long appleId) {
       if (apple <= 0) {
         return "对不起, 已经没货了!";
       } else {
         apple = apple - 1;
         log.info("购买成功, 现在apple数量为:{}", apple);
         return "购买成功!";
       }
     }
   
     /**
      * 不带分布式锁, 会出现超卖
      *
      * @param pearId
      */
     @GetMapping("/get/pear")
     public String getPear(Long pearId) {
       if (pear <= 0) {
         return "对不起, 已经没货了!";
       } else {
         pear = pear - 1;
         log.info("购买成功, 现在pear数量为:{}", pear);
         return "购买成功!";
       }
     }
   }
   ```

   



# 自定义接口实现

1.  自定义分布式锁key name 

   ```java
   
   ```

   

2.  自定义上锁失败业务

   ```java
   
   ```

   



# 点赞趋势

 [![Stargazers over time](https://starchart.cc/pearstack/lock-spring-boot-starter.svg)](https://starchart.cc/pearstack/lock-spring-boot-starter) 

 