package com.rango.common.lock;

import com.rango.common.zk.ZkUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheEvent;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.ZooDefs;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import java.util.concurrent.CountDownLatch;

@Slf4j
@Component
public class ZkDistributedLock implements DistributeLock, InitializingBean {

    private CuratorFramework curatorFramework = ZkUtil.getInstance();

    private CountDownLatch countDownLatch = new CountDownLatch(1);

    @Override
    public void afterPropertiesSet() {
        curatorFramework = curatorFramework.usingNamespace(ZkUtil.LOCK_NAMESPACE_PATH);
        String path = "/" + ZkUtil.LOCK_ROOT_PATH;
        try {
            if (curatorFramework.checkExists().forPath(path) == null) {
                curatorFramework
                        .create()
                        .creatingParentsIfNeeded()
                        .withMode(CreateMode.PERSISTENT)
                        .withACL(ZooDefs.Ids.OPEN_ACL_UNSAFE)
                        .forPath(path);
            }
            addWatcher(ZkUtil.LOCK_ROOT_PATH);
            log.info("root path 的 watcher 事件创建成功");
        } catch (Exception e) {
            log.error("connect zookeeper fail，please check the log >> {}", e.getMessage(), e);
        }
    }

    /**
     * 创建 watcher 事件
     */
    private void addWatcher(String path) throws Exception {
        String keyPath;
        if (path.equals(ZkUtil.LOCK_ROOT_PATH)) {
            keyPath = "/" + path;
        } else {
            keyPath = "/" + ZkUtil.LOCK_ROOT_PATH + "/" + path;
        }

        final PathChildrenCache cache = new PathChildrenCache(curatorFramework, keyPath, false);
        cache.start(PathChildrenCache.StartMode.POST_INITIALIZED_EVENT);
        cache.getListenable().addListener((client, event) -> {
            if (event.getType().equals(PathChildrenCacheEvent.Type.CHILD_REMOVED)) {
                String oldPath = event.getData().getPath();
                log.info("success to release lock for path:{}", oldPath);
                if (oldPath.contains(path)) {
                    //释放计数器，让当前的请求获取锁
                    countDownLatch.countDown();
                }
            }
        });
    }

    @Override
    public void acquireLock() {

    }

    /**
     * 获取分布式锁
     */
    @Override
    public void acquireLock(String path) {
        String keyPath = "/" + ZkUtil.LOCK_NAMESPACE_PATH + "/" + path;
        while (true) {
            try {
                curatorFramework
                        .create()
                        .creatingParentsIfNeeded()
                        .withMode(CreateMode.EPHEMERAL)  // 临时节点
                        .withACL(ZooDefs.Ids.OPEN_ACL_UNSAFE)
                        .forPath(keyPath);
                log.info("success to acquire lock for path:{}", keyPath);
                break;
            } catch (Exception e) {
                log.info("failed to acquire lock for path:{}", keyPath);
                log.info("while try again .......");
                if (countDownLatch.getCount() <= 0) {
                    countDownLatch = new CountDownLatch(1);
                }
                try {
                    // 阻塞等待锁释放，重新获取
                    countDownLatch.wait();
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
            }
        }
    }

    @Override
    public boolean releaseLock() {
        return false;
    }

    @Override
    public boolean releaseLock(String path) {
        String keyPath = "/" + ZkUtil.LOCK_NAMESPACE_PATH + "/" + path;
        try {
            if (curatorFramework.checkExists().forPath(keyPath) != null) {
                curatorFramework.delete().forPath(keyPath);
            }
        } catch (Exception e) {
            log.error("failed to release lock");
            return false;
        }
        return true;
    }
}
