package com.rango.jdk.proxy.factory;

import com.fml.proxy.CommInvocationHandler;
import com.fml.sdk.impl.SmsServiceImpl;
import net.sf.cglib.proxy.Enhancer;

public class CglibProxyFactory {

    public static Object getCommProxy(Class<?> clazz) {
        // 创建动态代理增强类
        Enhancer enhancer = new Enhancer();
        // 设置类加载器
        enhancer.setClassLoader(clazz.getClassLoader());
        // 设置被代理类
        enhancer.setSuperclass(clazz);
        // 设置方法拦截器
        enhancer.setCallback(new CommInvocationHandler(new SmsServiceImpl()));
        // 创建代理类
        return enhancer.create();
    }

}
