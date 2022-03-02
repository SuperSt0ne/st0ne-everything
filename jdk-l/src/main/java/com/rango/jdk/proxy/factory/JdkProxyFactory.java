package com.rango.jdk.proxy.factory;

import com.rango.jdk.proxy.CommInvocationHandler;

import java.lang.reflect.Proxy;

public class JdkProxyFactory {

    public static Object getCommProxy(Object target) {
        return Proxy.newProxyInstance(
                target.getClass().getClassLoader(),
                target.getClass().getInterfaces(),
                new CommInvocationHandler(target));
    }
}
