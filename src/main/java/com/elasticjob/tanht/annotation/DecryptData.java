package com.elasticjob.tanht.annotation;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
public @interface DecryptData {

    /**
     * 是否加密
     */
    boolean isEncrypt() default true;

    /**
     * 解密key
     */
    String decryptKey();

    /**
     * redis缓存key
     */
    String redisKey();
}
