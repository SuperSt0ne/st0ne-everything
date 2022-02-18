package com.rango.jdk.base;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class Base {
    public static void printResult(CompletableFuture<?> completableFuture) {
        Object join = completableFuture.join();
        if (join instanceof List) {
            ((List<?>) join).forEach(joinItem -> System.out.println("result item:" + joinItem));
        } else {
            System.out.println("result:" + join);
        }
    }

    public static void printThreadName() {
        System.out.println(Thread.currentThread().getName());
    }

    public static void printThreadName(String opt) {
        System.out.printf(Thread.currentThread().getName() + "-----> %s", opt).println();
    }

    public static void sleep(long second) {
        try {
            Thread.sleep(second * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void exceptionally() {
        throw new RuntimeException("返回一个异常");
    }
}
