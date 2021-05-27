package com.elasticjob.tanht.annotation.aspect;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.elasticjob.tanht.Entity.Ip;
import com.elasticjob.tanht.annotation.IpLimit;
import com.elasticjob.tanht.exception.TanException;
import com.elasticjob.tanht.service.IpService;
import com.elasticjob.tanht.util.TanUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


/**
 * @Author:tanht
 * ip限制切面处理
 */
@Aspect
@Component
@Order(Ordered.LOWEST_PRECEDENCE  - 1)
public class IpLimitAspect {

    @Autowired
    IpService ipService;
    private static final Logger logger = LoggerFactory.getLogger(IpLimitAspect.class);

    @Autowired
    private RedisTemplate<String,String> redisUtil;

    @Around("@within(com.elasticjob.tanht.annotation.IpLimit)||@annotation(com.elasticjob.tanht.annotation.IpLimit)")
    public Object ipLimit(ProceedingJoinPoint joinPoint) throws Throwable {
        logger.info("========开始校验该IP是否能访问该接口========");
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        //先获取方法，再获取类或者接口上的 IPLimit 注解
        IpLimit test = signature.getMethod().getAnnotation(IpLimit.class);
        if (test == null){
            test = joinPoint.getTarget().getClass().getAnnotation(IpLimit.class);
        }
        if (test == null){
            for (Class<?> cls : joinPoint.getTarget().getClass().getInterfaces()){
                test = cls.getAnnotation(IpLimit.class);
                if (test != null){
                    break;
                }
            }
        }

        String ip = TanUtil.getRequestIp(request);
        logger.info("========请求IP：{}========",ip);
        String key = test.key();
        boolean isPassIp = false;
        EntityWrapper<Ip> entityWrapper = new EntityWrapper<>();
        entityWrapper.eq("IP_KEY",key);
        List<Ip> ipList = ipService.selectList(entityWrapper);
        if(ipList.isEmpty() || ipList.size() == 0 || ipList == null){
            logger.info("========结束校验该ip{}是否合法========",ip);
            throw new TanException("1000","非法IP");
        }
        for (Ip ipEntity : ipList) {
            String passIp = ipEntity.getIp();
            if(ip.equals(passIp)){
                isPassIp = true;
            }
        }
        logger.info("========结束校验该ip{}是否合法========",ip);
        if(!isPassIp){
            throw new TanException("1000","非法IP");
        }
        logger.info("===========IP合法，开始处理请求============");
         return joinPoint.proceed();
    }

    @Before(value = "@within(com.elasticjob.tanht.annotation.IpLimit)||@annotation(com.elasticjob.tanht.annotation.IpLimit)")
    private void before(){
        logger.info("IpLimitAspect Before");
    }

    @After(value = "@within(com.elasticjob.tanht.annotation.IpLimit)||@annotation(com.elasticjob.tanht.annotation.IpLimit)")
    private void after(){
        logger.info("IpLimitAspect after");
    }

}
