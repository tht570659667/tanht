package com.elasticjob.tanht.annotation.aspect;

import com.alibaba.fastjson.JSONObject;
import com.elasticjob.tanht.annotation.DecryptData;
import com.elasticjob.tanht.util.TanUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

/**
 * @Author:tht
 * @Description: 解密注解处理切面，方法优先级大于类
 * @create:1:33 PM 4/27/2021
 */

@Aspect
@Component
@Order(Ordered.LOWEST_PRECEDENCE  - 2)
@Slf4j
public class DecryptDataAspect {


    @Around("@within(com.elasticjob.tanht.annotation.DecryptData)||@annotation(com.elasticjob.tanht.annotation.DecryptData)")
    public Object decryptData(ProceedingJoinPoint joinPoint) throws Throwable {
        log.info("【开始对加密数据进行解密】");
        long startTime = System.currentTimeMillis();
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        //先获取方法，再获取类或者接口上的 IPLimit 注解
        DecryptData decryptData = signature.getMethod().getAnnotation(DecryptData.class);
        if (decryptData == null){
            decryptData = joinPoint.getTarget().getClass().getAnnotation(DecryptData.class);
        }
        if (decryptData == null){
            for (Class<?> cls : joinPoint.getTarget().getClass().getInterfaces()){
                decryptData = cls.getAnnotation(DecryptData.class);
                if (decryptData != null){
                    break;
                }
            }
        }
        //开始解密请求数据
        Object[] param = joinPoint.getArgs();
        //先进行解密，再转化为json
        String result = toJSONString(param,decryptData.isEncrypt());
        return joinPoint.proceed((Object[]) JSONObject.parse(result));
    }


    /**
     * 将对象数组转换为JSON字符串
     * @param objects
     * @return
     */
    private String toJSONString(Object [] objects , boolean isDecrypt) {
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
            //如果需要解密，再进行解密
        } catch (Exception e) {
            log.error("将结果信息转换成json失败", e);
            resJson = objects.toString();
        }
        return resJson;
    }

}
