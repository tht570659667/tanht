package com.elasticjob.tanht.service;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.service.IService;
import com.elasticjob.tanht.Entity.User;

import java.util.List;

/**
 * @Author:Tanht
 * @Description:
 * @Date:2:09 PM 6/12/2020
 */


public interface UserService extends IService<User> {

    void sendSms();

    void sendMsg(JSONObject weather);

    List<JSONObject> queryTable(JSONObject tableName);
}
