package com.rango.jdk.juc;

import java.util.concurrent.TimeUnit;

public class VolatileTest {
    public static void main(String[] args) {
        A a = new A();
        a.start();
        for (; ; ) {
            //main线程对于A线程的改变不可见，加锁会清除main线程上的变量副本，重新从主内存读取
//            synchronized (a) {
                if (a.isFlag()) {
                    try {
                        TimeUnit.MILLISECONDS.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("有点东西");
                }
//            }
        }
    }
}

class A extends Thread {
    private boolean flag = false;

    public boolean isFlag() {
        return flag;
    }

    @Override
    public void run() {
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        flag = true;
        System.out.println("flag=" + flag);
    }
}
