package com.rango.jdk.juc;

public class BreadBuy {
    public static void main(String[] args) {

    }

    private static int BREAD_NUM = 10;

    private static synchronized void create() {
        BREAD_NUM++;
    }
}
