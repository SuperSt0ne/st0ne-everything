package com.rango.provider.bean;

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
}
