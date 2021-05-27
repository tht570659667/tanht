package com.elasticjob.tanht.exception;

import lombok.Data;

/**
 * @author tanht
 * @description:
 * @date 2020/5/9 13:56
 */
@Data
public class ExceptionInfo {
    private String code;
    private String message;
    private Object extend;
}
