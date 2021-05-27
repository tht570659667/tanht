package com.elasticjob.tanht.Entity;

import lombok.Data;

/**
 * @author tanht
 * @description:
 * @date 2020/5/9 9:55
 */
@Data
public class Result {

    private static final String SUCCESS_CODE = "0000";
    private static final String SUCCESS_MSG = "success";

    protected String respCode;
    protected String respMsg;

    public Result() {
        this.respCode = SUCCESS_CODE;
        this.respMsg = SUCCESS_MSG;
    }

    public void setCodeAndMsg(String respCode, String respMsg){
        this.respCode = respCode;
        this.respMsg = respMsg;
    }
}
