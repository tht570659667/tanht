package com.elasticjob.tanht.annotation;

import java.lang.annotation.*;


/**
 * IP限制注解
 * @author tanht
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
public @interface IpLimit {
    String key();
    int expireSeconds() default 600;
}
