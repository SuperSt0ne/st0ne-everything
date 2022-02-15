package com.rango.common.wheel;

import com.alibaba.fastjson.JSON;
import com.rango.common.dto.MessageDTO;
import com.rango.common.zk.ZkUtil;
import lombok.Data;
import lombok.extern.log4j.Log4j2;
import org.apache.curator.framework.CuratorFramework;
import org.apache.zookeeper.CreateMode;
import org.springframework.core.task.TaskExecutor;

import java.nio.charset.StandardCharsets;
import java.util.concurrent.CompletableFuture;

@Log4j2
@Data
public class ZkMessageCenter implements MessageCenter {

    private final CuratorFramework curatorFramework = ZkUtil.getInstance();

    private TaskExecutor taskExecutor;

    @Override
    public void sendMessage(String event, MessageDTO message) {
        CompletableFuture.runAsync(() -> {
            try {
                //message content
                byte[] bytes = JSON.toJSONString(message).getBytes(StandardCharsets.UTF_8);
                //path
                String path = ZkUtil.MESSAGE_ROOT_PATH + "/" + event;

                curatorFramework.create().orSetData().withMode(CreateMode.EPHEMERAL).forPath(path, bytes);

            } catch (Exception e) {
                log.error("An exception occurred while creating a ZooKeeper node", e);
            }
        }, taskExecutor);
    }
}
