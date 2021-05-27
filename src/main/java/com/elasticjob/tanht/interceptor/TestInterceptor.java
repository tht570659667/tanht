package com.elasticjob.tanht.interceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @Author:Tanht
 * @Description: 测试拦截器，在所有请求执行前进行拦截
 * 过滤器filter执行顺序在拦截器之前
 * @Date:10:38 AM 10/28/2020
 */


public class TestInterceptor implements HandlerInterceptor {

    private Logger logger = LoggerFactory.getLogger(TestInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) throws Exception {
        return true;
//        Cookie[] cookies = request.getCookies();
//        boolean isTest = false;
//        JSONObject result = new JSONObject();
//        if(cookies == null){
//            result.put("msg","用户标识不能为空");
//            returnJson(response,result.toJSONString());
//            return false;
//        }
//        for (Cookie cookie : cookies) {
//            //类似于内部人员模式
//            if(cookie.getName().equals("test") && cookie.getValue().equals("1")){
//                isTest = true;
//            }
//        }
//
//        //内部人员直接跳过拦截
//        if(isTest){
//            return true;
//        }
//
//        //如果非内部人员，直接驳回请求
//        result.put("msg","非内部人员不能访问");
//        returnJson(response,result.toJSONString());
//        logger.error("===============非内部人员访问，请求被拦截===============");
//        return false;
    }

    /**
     * 请求处理之后进行调用，但是在视图被渲染之前（Controller方法调用之后）
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) {
//         System.out.println("执行了TestInterceptor的postHandle方法");
    }

    /**
     * 在整个请求结束之后被调用，也就是在DispatcherServlet 渲染了对应的视图之后执行（主要是用于进行资源清理工作）
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
//        System.out.println("执行了TestInterceptor的afterCompletion方法");
    }


    /**
     * 返回json结果
     * @param response
     * @param json
     * @throws Exception
     */
    private void returnJson(HttpServletResponse response, String json) throws Exception {
        PrintWriter writer = null;
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=utf-8");
        try {
            writer = response.getWriter();
            writer.print(json);

        } catch (IOException e) {
            logger.error("response error", e);
        } finally {
            if (writer != null) {
                writer.close();
            }
        }

    }
    }
