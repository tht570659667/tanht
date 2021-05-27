package com.elasticjob.tanht.Entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

/**
 * @Author:Tanht
 * @Description:
 * @Date:2:18 PM 6/12/2020
 */

@TableName("THT_USER")
public class User{

    @TableId(value = "id",type = IdType.UUID)
    private String id;

    @TableField(value = "REAL_NAME")
    private String realName;

    @TableField("RECEIVE_NUM")
    private int receiveNum;

    @TableField(value = "USE_NUM")
    private int useNum;

    @TableField(value = "TELNUM")
    private String telNum;

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public int getReceiveNum() {
        return receiveNum;
    }

    public void setReceiveNum(int receiveNum) {
        this.receiveNum = receiveNum;
    }

    public int getUseNum() {
        return useNum;
    }

    public void setUseNum(int useNum) {
        this.useNum = useNum;
    }

    public String getTelNum() {
        return telNum;
    }

    public void setTelNum(String telNum) {
        this.telNum = telNum;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", realName='" + realName + '\'' +
                ", receiveNum=" + receiveNum +
                ", useNum=" + useNum +
                ", telNum='" + telNum + '\'' +
                '}';
    }
}
