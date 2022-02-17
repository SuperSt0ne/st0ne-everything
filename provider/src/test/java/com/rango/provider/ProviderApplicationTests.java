package com.rango.provider;

//import org.junit.jupiter.api.Test;
//import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

//@SpringBootTest
class ProviderApplicationTests {

    //    @Test
    void contextLoads() {
    }

    // 创建信号量
    static Semaphore semaphore = new Semaphore(2);

    public static void main(String[] args) {

        // 创建 5 个固定的线程数
        ExecutorService threadPool = Executors.newFixedThreadPool(5);

        // 定义执行任务
        Runnable runnable = () -> {
            // 拿到当前线程的名称
            String tname = Thread.currentThread().getName();
            System.out.println(String.format("老司机：%s，停车场外排队，时间：%s",
                    tname, new Date()));
            try {
                // 执行此行，让所有线程先排队等待进入停车场
                Thread.sleep(100);
                // 执行阻塞
                semaphore.acquire();
                System.out.println(String.format("老司机：%s，已进入停车场，时间：%s",
                        tname, new Date()));
                Thread.sleep(1000);
                System.out.println(String.format("老司机：%s，离开停车场，时间：%s",
                        tname, new Date()));
                // 释放锁
                semaphore.release();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };

        // 执行任务 1
        threadPool.submit(runnable);

        // 执行任务 2
        threadPool.submit(runnable);

        // 执行任务 3
        threadPool.submit(runnable);

        // 执行任务 4
        threadPool.submit(runnable);

        // 执行任务 5
        threadPool.submit(runnable);

        // 等线程池任务执行完之后关闭
        threadPool.shutdown();
    }

}
