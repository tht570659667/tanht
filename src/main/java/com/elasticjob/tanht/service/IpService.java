package com.elasticjob.tanht.service;

import com.baomidou.mybatisplus.service.IService;
import com.elasticjob.tanht.Entity.Ip;

/**
 * @Author:Tanht
 * @Description:
 * @Date:10:39 AM 9/14/2020
 */
public interface IpService extends IService<Ip> {

    boolean testManage();
}
