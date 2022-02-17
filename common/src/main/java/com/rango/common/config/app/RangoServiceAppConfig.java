package com.rango.common.config.app;

import com.rango.common.interceptor.RangoLogInterceptor;
import com.rango.common.interceptor.RangoRateLimitInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

public class RangoServiceAppConfig implements WebMvcConfigurer {

    @Autowired
    private RangoLogInterceptor rangoInterceptor;

    @Autowired
    private RangoRateLimitInterceptor rangoRateLimitInterceptor;

    /**
     * 拦截器策略添加自定义拦截器RangoLogInterceptor
     * @param registry InterceptorRegistry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(rangoInterceptor);
        registry.addInterceptor(rangoRateLimitInterceptor);
        WebMvcConfigurer.super.addInterceptors(registry);
    }
}
