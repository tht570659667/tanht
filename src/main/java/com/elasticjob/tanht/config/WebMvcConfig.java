package com.elasticjob.tanht.config;

import com.elasticjob.tanht.Entity.JsonDataResult;
import com.elasticjob.tanht.component.converter.CustomizeMappingJackson2HttpMessageConverter;
import com.elasticjob.tanht.component.converter.wraper.DefaultResponseWrapper;
import com.elasticjob.tanht.component.converter.wraper.ResponseWrapper;
import com.elasticjob.tanht.interceptor.TestInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * @Author:Tanht
 * @Description:
 * @Date:5:08 PM 9/14/2020
 */

@Configuration
public class WebMvcConfig extends WebMvcConfigurerAdapter {

    /**
     * 添加拦截器
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 权限拦截器
//        registry.addInterceptor(new TestInterceptor());
        InterceptorRegistration registration = registry.addInterceptor(new TestInterceptor());
        registration.addPathPatterns("/tanht/test/queryWeather");                      //所有路径都被拦截
//        registration.excludePathPatterns(                         //添加不拦截路径
//                "你的登陆路径",            //登录
//                "/**/*.html",            //html静态资源
//                "/**/*.js",              //js静态资源
//                "/**/*.css",             //css静态资源
//                "/**/*.woff",
//                "/**/*.ttf"
//        );
        super.addInterceptors(registry);
    }


    /**
     * Json消息转换器.
     * <p>对输入输出的JSON参数进行处理</p>
     * @param
     * @return:
     * @author: tanht
     * @date: 2020/05/09
     */
    @Bean
    public CustomizeMappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter() {
        List<MediaType> supportedMediaTypes = new ArrayList<MediaType>();
        supportedMediaTypes.add(MediaType.APPLICATION_JSON_UTF8);
        supportedMediaTypes.add(MediaType.TEXT_PLAIN);
        CustomizeMappingJackson2HttpMessageConverter messageConverter = new CustomizeMappingJackson2HttpMessageConverter();
        messageConverter.setResponseWrapper(getResponseWrapper());
        messageConverter.setSupportedMediaTypes(supportedMediaTypes);
        return messageConverter;
    }

    /**
     * 返回结果包装器.
     * 使用JsonResult包装
     * @param
     * @return:
     * @author: tanht
     * @date: 2020/05/09
     */
    @Bean("ResponseWrapper")
    public ResponseWrapper<JsonDataResult> getResponseWrapper() {
        return new DefaultResponseWrapper();
    }



    /**
     * 国际化文件解析配置
     * @return
     */
    @Bean(name = {"messageSource"})
    public ResourceBundleMessageSource getMessageResource() {
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setDefaultEncoding("UTF-8");
        messageSource.setBasenames(new String[]{"i18n/messages"});
        return messageSource;
    }

    /**
     * 默认解析器 其中locale表示默认语言
     */
    @Bean
    public LocaleResolver localeResolver() {
        SessionLocaleResolver localeResolver = new SessionLocaleResolver();
        localeResolver.setDefaultLocale(Locale.CHINA);
        return localeResolver;
    }
}
