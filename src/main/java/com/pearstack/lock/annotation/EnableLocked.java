package com.pearstack.lock.annotation;

import com.pearstack.lock.LockAutoConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * 启动分布式锁注释
 *
 * @author lihao3
 * @date 2021/12/15 16:22
 */
@Target(value = {ElementType.METHOD})
@Import({LockAutoConfiguration.class})
@Retention(value = RetentionPolicy.RUNTIME)
@Documented
public @interface EnableLocked {}