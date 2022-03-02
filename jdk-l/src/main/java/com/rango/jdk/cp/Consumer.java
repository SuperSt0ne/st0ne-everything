package com.rango.jdk.cp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;
import java.util.Queue;
import java.util.concurrent.TimeUnit;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Consumer implements Runnable {

    private Queue<Product> products;

    @Override
    public void run() {
        while (true) {
            synchronized (products) {

                if (products.isEmpty()) {
                    try {
                        System.out.println("[消费者] " + Thread.currentThread().getName() + " 等待中....");
                        products.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                Product poll = products.poll();
                if (Objects.isNull(poll)) {
                        System.out.println("[消费者] " + Thread.currentThread().getName() + " 等待中....");
                } else {
                    System.out.println("[消费者] " + Thread.currentThread().getName() + " 消费:" + poll.getName());

                    products.notifyAll();
                    try {
                        products.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

            }
        }
    }
}
