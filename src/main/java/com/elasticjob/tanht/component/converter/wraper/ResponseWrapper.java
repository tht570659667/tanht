package com.elasticjob.tanht.component.converter.wraper;

import com.elasticjob.tanht.exception.ExceptionInfo;

/**
 * @author tanht
 * @description:
 * @date 2020/5/9 14:06
 */
public interface ResponseWrapper<T> {
    T wrapData(Object var1);

    T wrapException(ExceptionInfo var1);
}
