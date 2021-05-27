package com.elasticjob.tanht.exception;

import lombok.Data;

/**
 * @Author:Tanht
 * @Description:自定义异常类
 * @Date:10:05 AM 9/14/2020
 */

@Data
public class TanException extends Exception {

    private String code;
    private String message;

    public TanException(String code)  {
        this.code = code;
    }

    public TanException(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
