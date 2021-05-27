package com.elasticjob.tanht.component.converter;

import com.elasticjob.tanht.Entity.JsonDataResult;
import com.elasticjob.tanht.component.converter.wraper.ResponseWrapper;
import com.elasticjob.tanht.exception.ExceptionInfo;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

import java.io.IOException;
import java.lang.reflect.Type;

/**
 * @author tanht
 * @description: 封装返回结果，固定以data,exception方式返回，参照卡券平台、排队机管理后台、配置平台。
 * @date 2020/5/9 11:04
 */
public class CustomizeMappingJackson2HttpMessageConverter extends MappingJackson2HttpMessageConverter {

    private ResponseWrapper responseWrapper;

    @Override
    protected void writeInternal(Object object, Type type, HttpOutputMessage outputMessage) throws IOException, HttpMessageNotWritableException {
        if (this.responseWrapper != null) {
            if (object instanceof ExceptionInfo) {
                ExceptionInfo exceptionInfo = (ExceptionInfo)object;
                object = this.responseWrapper.wrapException(exceptionInfo);
            } else if (object instanceof JsonDataResult){
                //如果直接返回的就是JsonDataResult 不做任何包装处理
            }else if(object instanceof Exception) {
                object = this.responseWrapper.wrapData(object);
            }
        }

        super.writeInternal(object, type, outputMessage);
    }

    public void setResponseWrapper(ResponseWrapper responseWrapper) {
        this.responseWrapper = responseWrapper;
    }
}
