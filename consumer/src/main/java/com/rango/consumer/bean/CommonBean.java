package com.rango.consumer.bean;

import com.rango.common.lock.ZkDistributedLock;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CommonBean {

    @Bean
    public ZkDistributedLock zkDistributedLock() {
        return new ZkDistributedLock();
    }
}
