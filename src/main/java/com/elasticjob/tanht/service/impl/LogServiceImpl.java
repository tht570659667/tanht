package com.elasticjob.tanht.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.elasticjob.tanht.Entity.LogInfo;
import com.elasticjob.tanht.mapper.LogMapper;
import com.elasticjob.tanht.service.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author:Tanht
 * @Description:
 * @Date:4:59 PM 11/2/2020
 */

@Service
public class LogServiceImpl extends ServiceImpl<LogMapper, LogInfo> implements LogService {

    @Autowired
    LogMapper logMapper;
}
