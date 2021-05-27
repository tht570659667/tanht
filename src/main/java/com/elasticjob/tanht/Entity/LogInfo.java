package com.elasticjob.tanht.Entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.Data;

import java.util.Date;

/**
 * @Author:Tanht
 * @Description:请求日志记录实体类
 * @Date:4:40 PM 11/2/2020
 */

@Data
@TableName(value = "REQUEST_LOG")
public class LogInfo {


    @TableId(type = IdType.AUTO)
    private int id;

    @TableField(value = "REQUESTURL")
    private String requestUrl;

    @TableField(value = "REQUESTPARAM")
    private String requestParam;

    @TableField(value = "RESPONSEPARAM")
    private String responseParam;

    @TableField(value = "EXCEPTIONINFO")
    private String exceptionInfo;

    @TableField(value = "STARTTIME")
    private Date startTime;

    @TableField(value = "ENDTIME")
    private Date endTime;

    @TableField(value = "IP")
    private String ip;

}
