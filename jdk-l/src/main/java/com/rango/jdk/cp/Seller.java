package com.rango.jdk.cp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Queue;
import java.util.concurrent.TimeUnit;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Seller implements Runnable {

    private Queue<Product> plan;

    public static int PLAN_NUM = 0;

    @Override
    public void run() {
        while (true) {
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("[商 家] 检查 PRODUCTS: " + Mail.PRODUCTS.size() + ", plan: " + plan.size() + ", PLAN_NUM: " + PLAN_NUM);
            if (Mail.PRODUCTS.size() < 5 && plan.size() == 0 && PLAN_NUM == 0) {

                PLAN_NUM = Mail.MAX_COUNT - Mail.PRODUCTS.size();
                int batch = Mail.PRODUCT_BATCH.incrementAndGet();
                System.out.println("[商 家] 发起订单,批次:" + batch + ",数量:" + PLAN_NUM);

                synchronized (plan) {
                    plan.notifyAll();
                }
            }
        }

    }
}
