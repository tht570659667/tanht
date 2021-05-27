package com.elasticjob.tanht.controller.baseController;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.IService;
import com.elasticjob.tanht.exception.TanException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.Serializable;
import java.util.List;

/**
 * @Author:Tanht
 * @Description:接口公共方法（CURD)
 * @Date:4:09 PM 9/14/2020
 */


public class BaseController<Entity,ID extends Serializable> {

    @Autowired
    IService<Entity> iService;

    /**
     * 查询方法
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "list")
    public List<Entity> query(){
        EntityWrapper<Entity> entityWrapper = new EntityWrapper();
        List<Entity> list = iService.selectList(entityWrapper);
        return list;
    }

    /**
     * 新增实体
     * @param entity
     * @return
     * @throws TanException
     */
    @ResponseBody
    @RequestMapping(value = "add")
    public boolean insert(@RequestBody Entity entity) throws TanException {
        if(entity == null){
            throw new TanException("1001","请求实体类异常");
        }
        return iService.insert(entity);
    }
}
