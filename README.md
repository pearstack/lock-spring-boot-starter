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
    <a href="https://github.com/pearstack/lock-spring-boot-starter/stargazers"><img alt="GitHub stars" src="https://img.shields.io/github/stars/pearstack/lock-spring-boot-starter"></a>
    <a href="https://github.com/pearstack/lock-spring-boot-starter/blob/master/LICENSE"><img alt="GitHub license" src="https://img.shields.io/github/license/pearstack/lock-spring-boot-starter"></a>
    <a href="https://github.com/pearstack/lock-spring-boot-starter/network"><img alt="GitHub forks" src="https://img.shields.io/github/forks/pearstack/lock-spring-boot-starter"></a>
    <img alt="Lines of code" src="https://img.shields.io/tokei/lines/github/pearstack/lock-spring-boot-starter">
    <img alt="GitHub code size in bytes" src="https://img.shields.io/github/languages/code-size/lihao0324/lock-spring-boot-starter">


## 工程简介

点击链接加入群聊【Pear Stack】讨论交流：[293972109](https://jq.qq.com/?_wv=1027&k=lw2E7QlX)

国内镜像: 【Gitee】：[lock-spring-boot-starter](https://gitee.com/pear-stack/lock-spring-boot-starter)

1. 简单描述

   `lock-spring-boot-starter` 一款分布式锁, 支持使用 `Redis` 和 `ZooKeeper`, 开箱即用, 快捷方便

2. 特点

   1. `spring-boot-starter`组件开箱即用, 快捷方便
   2. 支持`自定义key`, 以及`spring el`表达式
   3. 将生成key的方法和上锁失败的异常方法作为接口, 可以自定义实现层, 更加自由

3. [依赖版本](https://github.com/pearstack/lock-spring-boot-starter/network/dependencies)

   |              框架名称               | 版本号  |                  官网                  |
   | :---------------------------------: | :-----: | :------------------------------------: |
   |       spring-boot-starter-aop       |  2.6.2  | https://spring.io/projects/spring-boot |
   |   spring-boot-starter-validation    |  2.6.2  | https://spring.io/projects/spring-boot |
   |      spring-integration-redis       |  2.6.2  | https://spring.io/projects/spring-boot |
   |   spring-boot-starter-data-redis    |  2.6.2  | https://spring.io/projects/spring-boot |
   |    redisson-spring-boot-starter     | 3.16.7  |          http://redisson.org           |
   |    spring-integration-zookeeper     |  2.6.2  | https://spring.io/projects/spring-boot |
   | spring-boot-configuration-processor |  2.6.2  | https://spring.io/projects/spring-boot |
   |             hutool-core             | 5.7.17  |   https://github.com/dromara/hutool    |
   |               lombok                | 1.18.22 |       https://projectlombok.org        |
   
   

## 安装

1. Redisson 模式 ![Maven Central](https://img.shields.io/maven-central/v/io.github.pearstack/lock-redisson-spring-boot-starter?style=flat-square)

   1. Apache Maven

      ```xml
      <dependency>
        <groupId>io.github.pearstack</groupId>
        <artifactId>lock-redisson-spring-boot-starter</artifactId>
        <version>${last.version}</version>
      </dependency>
      ```

   2. Gradle Groovy DSL

      ```groovy
      implementation 'io.github.pearstack:lock-redisson-spring-boot-starter:${last.version}'
      ```

2. Redis Template模式 ![Maven Central](https://img.shields.io/maven-central/v/io.github.pearstack/lock-redis-template-spring-boot-starter?style=flat-square)

   1. Apache Maven

      ```xml
      <dependency>
        <groupId>io.github.pearstack</groupId>
        <artifactId>lock-redis-template-spring-boot-starter</artifactId>
        <version>${last.version}</version>
      </dependency>
      ```

   2. Gradle Groovy DSL

      ```groovy
      implementation 'io.github.pearstack:lock-redis-template-spring-boot-starter:${last.version}'
      ```

3. ZooKeeper 模式 ![Maven Central](https://img.shields.io/maven-central/v/io.github.pearstack/lock-zookeeper-spring-boot-starter?style=flat-square)

   1. Apache Maven

      ```xml
      <dependency>
        <groupId>io.github.pearstack</groupId>
        <artifactId>lock-zookeeper-spring-boot-starter</artifactId>
        <version>${last.version}</version>
      </dependency>
      ```

   2. Gradle Groovy DSL

      ```groovy
      implementation 'io.github.pearstack:lock-zookeeper-spring-boot-starter:${last.version}'
      ```

## 快速开始

PS: 更多细节请看[帮助文档](https://github.com/pearstack/lock-spring-boot-starter/wiki/%E4%B8%AD%E6%96%87%E5%B8%AE%E5%8A%A9%E6%96%87%E6%A1%A3)

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



## 特别鸣谢

1. 茶猫云赞助服务器 : https://www.cmy.cn



## 使用lock-spring-boot-starter的开源项目

暂无~(欢迎留言)



## 友情链接

暂无~ (欢迎交换链接)



## 趋势图

 [![Stargazers over time](https://starchart.cc/pearstack/lock-spring-boot-starter.svg)](https://starchart.cc/pearstack/lock-spring-boot-starter) 

 
