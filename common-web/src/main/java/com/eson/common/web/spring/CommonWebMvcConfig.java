package com.eson.common.web.spring;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.eson.common.web.handler.AppExceptionHandler;

/**
 * @author dengxiaolin
 * @since 2021/04/23
 */
@Configuration
public class CommonWebMvcConfig {
    @Bean
    public AppExceptionHandler appExceptionHandler() {
        return new AppExceptionHandler();
    }

    @Bean(name = "multipartResolver")
    public MultipartResolver multipartResolver() {
        CommonsMultipartResolver resolver = new CommonsMultipartResolver();
        resolver.setDefaultEncoding("UTF-8");

        // resolveLazily属性启用是为了推迟文件解析，以在在UploadAction中捕获文件大小异常
        resolver.setResolveLazily(true);
        // Set the maximum allowed size (in bytes) before uploads are written to disk.
        resolver.setMaxInMemorySize(40960);
        // 上传文件大小 60M 60*1024*1024
        resolver.setMaxUploadSize(60 * 1024 * 1024);
        return resolver;
    }
}
