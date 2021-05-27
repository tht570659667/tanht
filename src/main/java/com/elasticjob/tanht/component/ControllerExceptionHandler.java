package com.elasticjob.tanht.component;

import com.elasticjob.tanht.exception.ExceptionInfo;
import com.elasticjob.tanht.exception.TanException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * @author tanht
 * @description: 全局异常处理
 * @date 2020/5/9 13:57
 */
@Slf4j
@ControllerAdvice
public class ControllerExceptionHandler {

    @Autowired
    private MessageSource messageSource;

    /**
     * 针对TanException异常的处理
     * @param e
     * @return
     */
    @ResponseBody
    @ExceptionHandler(TanException.class)
    public ExceptionInfo tanExceptionHandle(TanException e){
        String respCode = e.getCode();
        String respMsg = e.getMessage();
        ExceptionInfo exceptionInfo = new ExceptionInfo();
        try{
        //若异常类没有Message,则从配置文件中读取
        if(StringUtils.isBlank(respMsg)){
            respMsg = messageSource.getMessage(respCode, null, LocaleContextHolder.getLocale());
        }
            exceptionInfo.setCode(respCode);
            exceptionInfo.setMessage(respMsg);
        }catch (Exception exception){
            exceptionInfo.setMessage(exception.toString());
            exception.printStackTrace();
        }
        return exceptionInfo;
    }

    public static String getStackTraceInfo(Throwable t) {
        StringWriter sw = new StringWriter();
        t.printStackTrace(new PrintWriter(sw, true));
        return sw.getBuffer().toString();
    }
}


