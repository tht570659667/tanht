package com.elasticjob.tanht.Entity;

import lombok.Data;

/**
 * @author haoxm
 * @description:
 * @date 2020/5/9 9:55
 */
@Data
public class JsonDataResult<T> extends Result{
    private T data;

    public JsonDataResult() {
        super();
    }

    public JsonDataResult(String respCode, String respMsg) {
        super();
        setCodeAndMsg(respCode, respMsg);
    }

}
