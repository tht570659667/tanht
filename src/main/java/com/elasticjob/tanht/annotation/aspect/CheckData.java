package com.elasticjob.tanht.annotation.aspect;


import java.lang.annotation.*;

/**
 * 校验用户所传参数注解
 * @author tanht
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
public @interface CheckData {
    boolean isCheck() default true;
    int expireSeconds() default 600;
}
