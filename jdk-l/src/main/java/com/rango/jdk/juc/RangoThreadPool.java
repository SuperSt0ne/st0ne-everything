package com.rango.jdk.juc;

import com.rango.basic.exception.RangoException;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

public class RangoThreadPool {
    BlockingQueue<Runnable> taskQueue;  //存放任务的阻塞队列
    List<YesThread> threads; //线程列表

    public RangoThreadPool(BlockingQueue<Runnable> taskQueue, int threadSize) {
        this.taskQueue = taskQueue;
        threads = new ArrayList<>(threadSize);
        // 初始化线程，并定义名称
        IntStream.rangeClosed(1, threadSize).forEach((i) -> {
            YesThread thread = new YesThread("rango-task-thread-" + i);
            thread.start();
            threads.add(thread);
        });
    }

    //提交任务只是往任务队列里面塞任务
    public void execute(Runnable task) throws InterruptedException {
        taskQueue.put(task);
    }

    class YesThread extends Thread { //自定义一个线程
        public YesThread(String name) {
            super(name);
        }

        @Override
        public void run() {
            for (; ; ) {//死循环
                Runnable task;
                try {
                    task = taskQueue.take(); //不断从任务队列获取任务
                } catch (InterruptedException e) {
                    throw new RangoException(e.getMessage());
                }
                task.run(); //执行
            }
        }
    }

    public static void main(String[] args) {
        RangoThreadPool pool = new RangoThreadPool(new LinkedBlockingQueue<>(20), 5);
        for (; ; ) {
            try {
                pool.execute(() -> {
                    try {
                        TimeUnit.SECONDS.sleep(2);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println(Thread.currentThread().getName() + " 执行任务");
                });
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}



