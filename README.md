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

1. Redis 模式

```xml
        <dependency>
            <groupId>com.pear-stack</groupId>
            <artifactId>lock-redis-spring-boot-starter</artifactId>
            <version>{{last.version}}</version>
        </dependency>
```



1. ZooKeeper 模式

```xml
    <dependency>
        <groupId>com.pear-stack</groupId>
        <artifactId>lock-zookeeper-spring-boot-starter</artifactId>
        <version>{{last.version}}</version>
    </dependency>
```