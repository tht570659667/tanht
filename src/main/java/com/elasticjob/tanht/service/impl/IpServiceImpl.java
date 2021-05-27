package com.elasticjob.tanht.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.elasticjob.tanht.Entity.Ip;
import com.elasticjob.tanht.exception.TanException;
import com.elasticjob.tanht.mapper.IpMapper;
import com.elasticjob.tanht.service.IpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Author:Tanht
 * @Description:
 * @Date:10:39 AM 9/14/2020
 */

@Service
public class IpServiceImpl extends ServiceImpl<IpMapper, Ip> implements IpService {
    @Autowired
    IpMapper ipMapper;

    private final static String TMALL_URL = "http://127.0.0.1:8080/admin_category_list";

    //调用天猫商城接口
    /**
     * 事务回滚测试，当发生异常时，之前处理的数据进行撤回
     * 就算insert方法执行完成，但后面的方法如果报错，数据库数据也会进行回滚
     * @return
     */
    @Override
    @Transactional(rollbackFor=Exception.class ,  noRollbackFor = TanException.class , propagation = Propagation.REQUIRED)
    public boolean testManage(){
        try{
            Ip ip = new Ip();
            ip.setIp("10.189.xx.xx");
            ip.setIpKey("key");
            insert(ip);
            int a = 10 / 0;
            ip.setIpKey("456");
            updateById(ip);
        }catch (Exception e){
            //设置数据回滚
//            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            throw e;
        }
        return true;
    }
}
