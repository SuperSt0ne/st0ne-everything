package com.rango.consumer.bean;

import com.rango.common.lock.ZkDistributedLock;
import com.rango.common.wheel.ZkMessageListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
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

    @Bean(name = "zkMessageListener")
    public ZkMessageListener zkMessageListener() {
        return new ZkMessageListener();
    }
}
