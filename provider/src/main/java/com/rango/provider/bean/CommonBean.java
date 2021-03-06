package com.rango.provider.bean;

import com.rango.common.interceptor.RangoLogInterceptor;
import com.rango.common.config.app.RangoServiceAppConfig;
import com.rango.common.interceptor.RangoRateLimitInterceptor;
import com.rango.common.limit.SemaphoreRateLimit;
import com.rango.common.lock.ZkDistributedLock;
import com.rango.common.wheel.ZkMessageCenter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
//@ImportResource("classpath:spring.xml") //也可通过xml导入bean
public class CommonBean {

    @Bean(name = "zkDistributedLock")
    public ZkDistributedLock zkDistributedLock() {
        return new ZkDistributedLock();
    }

    @Bean(name = "taskExecutor")
    public ThreadPoolTaskExecutor threadPoolTaskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(4);
        executor.setMaxPoolSize(16);
        executor.setQueueCapacity(128);
        return executor;
    }

    @Bean(name = "zkMessageCenter")
    public ZkMessageCenter zkMessageCenter() {
        ZkMessageCenter zkMessageCenter = new ZkMessageCenter();
        zkMessageCenter.setTaskExecutor(threadPoolTaskExecutor());
        return zkMessageCenter;
    }

    @Bean
    public RangoLogInterceptor rangoInterceptor() {
        return new RangoLogInterceptor();
    }

    @Bean
    public RangoRateLimitInterceptor rangoRateLimitInterceptor() {
        RangoRateLimitInterceptor rangoRateLimitInterceptor = new RangoRateLimitInterceptor();
        rangoRateLimitInterceptor.setSemaphoreRateLimit(semaphoreRateLimit());
        return rangoRateLimitInterceptor;
    }

    @Bean
    public RangoServiceAppConfig rangoServiceInterceptorConfig() {
        return new RangoServiceAppConfig();
    }

    @Bean
    public SemaphoreRateLimit semaphoreRateLimit() {
        return new SemaphoreRateLimit();
    }
}
