package com.rango.common.zk;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;

public class ZkUtil {

    public static final String MESSAGE_ROOT_PATH = "/message";

    public static final String LOCK_NAMESPACE_PATH = "lock";

    public static final String LOCK_ROOT_PATH = "root";

    private static final int BASE_SLEEP_TIME = 1000;

    //Retry strategy. Retry 3 times, and will increase the sleep time between retries.
    private static final int MAX_RETRIES = 3;

    public static CuratorFramework getInstance() {
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(BASE_SLEEP_TIME, MAX_RETRIES);
        CuratorFramework zkClient = CuratorFrameworkFactory.builder()
                // the server to connect to (can be a server list)
                .connectString("127.0.0.1:2181")
                .retryPolicy(retryPolicy)
                .build();
        zkClient.start();
        return zkClient;
    }
}
