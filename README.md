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



# 工程简介

1. 简单描述

   `lock-spring-boot-starter` 一款分布式锁, 支持使用 `Redis` 和 `ZooKeeper`, 开箱即用, 快捷方便

2. 特点

   1. `spring-boot-starter`组件开箱即用, 快捷方便
   2. 支持自定义key, 以及spring el表达式
   3. 将生成key的方法和上锁失败的异常方法作为接口, 可以自定义实现层, 更加自由

3. 框架所用技术

   |            框架名称             | 所用版本 |          官网          |
   | :-----------------------------: | :------: | :--------------------: |
   |           spring-boot           |  2.4.1   |   https://spring.io    |
   | spring-boot-starter-integration |  2.4.1   |   https://spring.io    |
   |     spring-boot-starter-aop     |  2.4.1   |   https://spring.io    |
   |           hutool-core           |  5.7.17  | https://hutool.cn/docs |
   |    spring-integration-redis     |  2.4.1   |   https://spring.io    |
   | spring-boot-starter-data-redis  |  2.4.1   |   https://spring.io    |
   |  spring-integration-zookeeper   |  2.4.1   |   https://spring.io    |

   

# 使用方法

1. 引入jar包

   Last Version : [![Maven Central](https://img.shields.io/badge/Maven%20Central-0.0.2-brightgreen)](https://search.maven.org/search?q=io.github.pearstack)

   1. Redis 模式

      ```xml
              <dependency>
                  <groupId>io.github.pearstack</groupId>
                  <artifactId>lock-redis-spring-boot-starter</artifactId>
                  <version>{{last.version}}</version>
              </dependency>
      ```

   2. ZooKeeper 模式

      ```xml
          <dependency>
              <groupId>io.github.pearstack</groupId>
              <artifactId>lock-zookeeper-spring-boot-starter</artifactId>
              <version>{{last.version}}</version>
          </dependency>
      ```

2. 全局配置

   ```yaml
   
   ```

   

3. 单独配置

   ```java
   
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

 