package com.rango.jdk.cp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Queue;
import java.util.concurrent.TimeUnit;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Provider implements Runnable {

    private Queue<Product> products;

    private Queue<Product> plan;

    @Override
    public void run() {
        while (true) {
            if (Seller.PLAN_NUM == 0) {
                try {
                    TimeUnit.SECONDS.sleep(2);
                    System.out.println("[生产者] " + Thread.currentThread().getName() + " 等待商家订单");

                    continue;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            synchronized (plan) {
                if (Seller.PLAN_NUM > 0) {
                    System.out.println("[生产者] " + Thread.currentThread().getName() + " 获取到商家订单");
                }
                while (true) {
                    if (plan.size() == Seller.PLAN_NUM) {
                        synchronized (products) {
                            if (plan.size() <= 0) {
                                break;
                            }
                            while (plan.size() > 0) {
                                products.offer(plan.poll());
                            }
                            Seller.PLAN_NUM = 0;
                            System.out.println("[生产者] " + Thread.currentThread().getName() + " 完成商家订单");
                            Mail.PRODUCT_NUMBER.set(0);

                            products.notifyAll();
                            break;
                        }
                    } else {
                        try {
                            TimeUnit.MILLISECONDS.sleep(500);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                        Product product = create(plan);
                        System.out.println("[生产者] " + Thread.currentThread().getName() + " 生产:" + product.getName());
                    }
                }
            }
        }
    }

    private Product create(Queue<Product> plan) {
        Product product = new Product();
        product.setName("商品" + Mail.PRODUCT_BATCH.get() + "-" + Mail.PRODUCT_NUMBER.incrementAndGet());
        plan.offer(product);
        return product;
    }
}
