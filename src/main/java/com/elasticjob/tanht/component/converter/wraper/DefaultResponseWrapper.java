package com.elasticjob.tanht.component.converter.wraper;

import com.elasticjob.tanht.Entity.JsonDataResult;
import com.elasticjob.tanht.exception.ExceptionInfo;

/**
 * @author tanht
 * @description:
 * @date 2020/5/9 14:07
 */
public class DefaultResponseWrapper implements ResponseWrapper<JsonDataResult> {
    public DefaultResponseWrapper() {
    }

    /**
     * 成功请求后返回数据封装
     * @param data
     * @return
     */
    @Override
    public JsonDataResult wrapData(Object data) {
        JsonDataResult result = new JsonDataResult();
        result.setData(data);
        return result;
    }

    /**
     * 异常类封装
     * @param exceptionInfo
     * @return
     */
    @Override
    public JsonDataResult wrapException(ExceptionInfo exceptionInfo) {
        JsonDataResult jsonResult = new JsonDataResult(exceptionInfo.getCode(), exceptionInfo.getMessage());
        return jsonResult;
    }
}
