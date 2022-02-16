package com.rango.common.config.service;

import com.rango.common.interceptor.RangoLogInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

public class RangoServiceAppConfig implements WebMvcConfigurer {

    @Autowired
    private RangoLogInterceptor rangoInterceptor;

    /**
     * 拦截器策略添加自定义拦截器RangoLogInterceptor
     * @param registry InterceptorRegistry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(rangoInterceptor);
        WebMvcConfigurer.super.addInterceptors(registry);
    }
}
