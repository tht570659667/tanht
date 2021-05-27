package com.elasticjob.tanht.Entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;

/**
 * @Author:Tanht
 * @Description:
 * @Date:10:37 AM 9/14/2020
 */


@TableName(value = "IP_LIMIT")
public class Ip {
    @TableField("ID")
    private int id;
    @TableField("IP")
    private String ip;
    @TableField("IP_KEY")
    private String ipKey;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getIpKey() {
        return ipKey;
    }

    public void setIpKey(String ipKey) {
        this.ipKey = ipKey;
    }

    @Override
    public String toString() {
        return "Ip{" +
                "id=" + id +
                ", ip='" + ip + '\'' +
                ", ipKey='" + ipKey + '\'' +
                '}';
    }
}
