package com.rango.common.wheel;

import com.alibaba.fastjson.JSON;
import com.rango.basic.annotation.CatchMessage;
import com.rango.common.dto.MessageDTO;
import com.rango.common.zk.ZkUtil;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheEvent;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheListener;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Log4j2
public class ZkMessageListener implements BeanPostProcessor, InitializingBean {

    private final PathChildrenCache cache;

    private List<String> catchMessageEventList = new ArrayList<>();


    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        for (Method method : ReflectionUtils.getAllDeclaredMethods(bean.getClass())) {
            CatchMessage catchMessage = AnnotationUtils.findAnnotation(method, CatchMessage.class);
            if (Objects.isNull(catchMessage)) continue;
            String catchEvent = catchMessage.event();
            if (StringUtils.isBlank(catchEvent)) continue;
            catchMessageEventList.add(catchEvent);
        }
        return bean;
    }

    public ZkMessageListener() {
        CuratorFramework client = ZkUtil.getInstance();
        try {
            client.create().creatingParentsIfNeeded().withMode(CreateMode.PERSISTENT).forPath(ZkUtil.MESSAGE_ROOT_PATH, new byte[0]);
        } catch (KeeperException.NodeExistsException e) {
            // ignore
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        this.cache = new PathChildrenCache(client, ZkUtil.MESSAGE_ROOT_PATH, true);
        try {
            this.cache.start();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        PathChildrenCacheListener listener = (client, event) -> {
            if (event.getType() == PathChildrenCacheEvent.Type.CHILD_ADDED) {
                MessageDTO message = JSON.parseObject(new String(event.getData().getData(), StandardCharsets.UTF_8), MessageDTO.class);
                if (catchMessageEventList.contains(message.getEvent())) {
                    System.out.println("listen message:" + message);
                }
            }
        };
        this.cache.getListenable().addListener(listener);
    }
}
