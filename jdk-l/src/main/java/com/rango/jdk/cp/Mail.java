package com.rango.jdk.cp;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.atomic.AtomicInteger;

public class Mail {

    /**
     * 订单批次
     */
    public static volatile AtomicInteger PRODUCT_BATCH = new AtomicInteger();

    /**
     * 商品编号
     */
    public static volatile AtomicInteger PRODUCT_NUMBER = new AtomicInteger();

    /**
     * 商品最大数量
     */
    public static final int MAX_COUNT = 20;

    /**
     * 商品
     */
    public static Queue<Product> PRODUCTS = new LinkedList<>();

    /**
     * 订单计划
     */
    public static Queue<Product> PLAN = new LinkedList<>();

    public static void main(String[] args) {
        Thread p1 = new Thread(new Provider(PRODUCTS, PLAN));
        p1.setName("PROVIDER-1");
        p1.start();
        Thread p2 = new Thread(new Provider(PRODUCTS, PLAN));
        p2.setName("PROVIDER-2");
        p2.start();

        Thread c1 = new Thread(new Consumer(PRODUCTS));
        c1.setName("CONSUMER-1");
        c1.start();
        Thread c2 = new Thread(new Consumer(PRODUCTS));
        c2.setName("CONSUMER-2");
        c2.start();
        Thread c3 = new Thread(new Consumer(PRODUCTS));
        c3.setName("CONSUMER-3");
        c3.start();
        Thread s1 = new Thread(new Seller(PLAN));
        s1.setName("SELLER-1");
        s1.start();
    }
}
