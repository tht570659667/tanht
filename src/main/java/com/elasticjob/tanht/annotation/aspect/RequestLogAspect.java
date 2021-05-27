package com.elasticjob.tanht.annotation.aspect;

import com.alibaba.fastjson.JSONObject;
import com.elasticjob.tanht.Entity.LogInfo;
import com.elasticjob.tanht.exception.TanException;
import com.elasticjob.tanht.service.LogService;
import com.elasticjob.tanht.util.TanUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * controller请求日志记录切面，记录请求数据，返回数据，ip，请求时间，结束时间，异常信息
 * @author tanht
 */
@Aspect
@Component
@Order(Ordered.LOWEST_PRECEDENCE  - 1)
public class RequestLogAspect {

    @Autowired
    LogService logService;

    private Logger logger = LoggerFactory.getLogger(RequestLogAspect.class);

    ThreadLocal<Long> startTime = new ThreadLocal<>();
    /**
     * 定义控制器切点
     */
    @Pointcut("within(com.elasticjob.tanht.controller..*)")
    public void RequestControllerLogAspect() {
    }

    /**
     * 定义实现类切点
     */
    public void serviceImplAspect(){

    }

    /**
     * 记录请求日志
     * @param joinPoint
     * @return
     * @throws Throwable
     */
    @Around(value = "RequestControllerLogAspect()")
    public Object aroundRequest(ProceedingJoinPoint joinPoint) throws Throwable {
        Object returnVal = null;
        LogInfo logInfo = new LogInfo();
        logInfo.setStartTime(new Date());
        //请求参数
        Object[] param = joinPoint.getArgs();
        //先执行业务代码，如果报错，catch处理异常，finally记录请求参数以及返回结果
        try {
            startTime.set(System.currentTimeMillis());
            returnVal = joinPoint.proceed();
        } catch (Exception e) {
            logger.error("RequestLogAspect error === { 处理业务逻辑发生异常 }");
            logger.error("RequestLogAspect error ===> {} ", e.getMessage());
            //记录异常种类以及异常信息
            logInfo.setExceptionInfo(e.toString());
            throw new TanException("-5000",  e.getMessage());
        } finally {
            try {
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            logger.info("当前请求地址：{}，请求IP：{}",request.getRequestURI(),TanUtil.getRequestIp(request));
            //获取请求参数
            logInfo.setRequestParam(toJSONString(param));
            //响应参数
            logInfo.setResponseParam(toJSONString(new Object[]{returnVal}));
            //请求地址
            logInfo.setRequestUrl(request.getRequestURI());
            logInfo.setEndTime(new Date());
            logInfo.setIp(TanUtil.getRequestIp(request));
            logService.insert(logInfo);
            } catch (Exception e) {
                logger.error("日志记录入表异常{}", e);
            }
            logger.info("该请求记录如下：{}",logInfo.toString());
            logger.info("耗时（毫秒）：" +  (System.currentTimeMillis() - startTime.get()) + "ms");
        }
        return returnVal;
    }

    /**
     * 将对象数组转换为JSON字符串
     * @param objects
     * @return
     */
    private String toJSONString(Object [] objects) {
        String resJson;
        try {
            // 将对象转成json字符串,若objects为空，返回空字符串
            if (objects != null && objects.length > 0){
                //ServletRequest 无法JSON转换，这里转换为用户IP地址
                for (int i = 0; i < objects.length; i++) {
                    if (objects[i] instanceof ServletRequest){
                        HttpServletRequest request = (HttpServletRequest) objects[i];
                        JSONObject reqJson = new JSONObject();
                        reqJson.put("ip", TanUtil.getRequestIp(request));
                        reqJson.put("parameters", request.getParameterMap());
                        objects[i] = reqJson;
                    }
                }
                //如果只有一个对象，JSON只转换该对象
                if (objects.length == 1){
                    resJson = JSONObject.toJSONString(objects[0]);
                }else {
                    resJson = JSONObject.toJSONString(objects);
                }
            }else {
                resJson = "";
            }
        } catch (Exception e) {
            logger.error("将结果信息转换成json报文失败", e);
            resJson = objects.toString();
        }
        return resJson;
    }

    @Before(value = "RequestControllerLogAspect()")
    private void before(){
        logger.info("RequestControllerLogAspect Before");
    }

    @After(value = "RequestControllerLogAspect()")
    private void after(){
        logger.info("RequestControllerLogAspect after");
    }
}
