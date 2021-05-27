package com.elasticjob.tanht.mapper;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.elasticjob.tanht.Entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author:Tanht
 * @Description:
 * @Date:2:23 PM 6/12/2020
 */

@Mapper
public interface UserMapper extends BaseMapper<User> {

    /**
     * 根据表名查询数据
     * @param tableName
     * @return
     */
    List<JSONObject> queryTable(@Param(value = "tableName") String tableName);
}
