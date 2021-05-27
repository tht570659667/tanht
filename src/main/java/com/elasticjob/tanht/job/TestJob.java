package com.elasticjob.tanht.job;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;
import com.elasticjob.tanht.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Author:Tanht
 * @Description:定时任务学习
 * @Date:12:30 PM 6/12/2020
 */


public class TestJob implements SimpleJob{

    @Autowired
    UserService userService;

    Logger logger = LoggerFactory.getLogger(TestJob.class);

    private final String ID = "abcdefghijklmnoptrstuvwxyzasdfas";

    private static String twentyTwo = "22:00";


    @Override
    public void execute(ShardingContext shardingContext) {
        logger.info("=============开始发送邮件==========");
        userService.sendSms();
        logger.info("=============停止发送邮件==========");
    }




}
