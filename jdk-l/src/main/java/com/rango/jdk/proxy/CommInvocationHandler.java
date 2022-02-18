package com.rango.jdk.proxy;

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * 通用动态代理类
 */
public class CommInvocationHandler implements InvocationHandler, MethodInterceptor {

    /**
     * 代理类中的真实对象
     */
    private final Object target;

    public CommInvocationHandler(Object target) {
        this.target = target;
    }

    /**
     * override method by InvocationHandler
     *
     * jdk动态代理
     *
     * @param proxy  动态生成的代理类
     * @param method 与代理类对象调用的方法相对应
     * @param args   当前 method 方法的参数
     * @return
     * @throws Throwable
     */
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("before do method:" + method.getName());
        Object invoke = method.invoke(target, args);
        System.out.println("after do method:" + method.getName());
        return invoke;
    }

    /**
     * override method by MethodInterceptor
     *
     * cglib动态代理
     *
     * @param obj    代理对象（增强的对象）
     * @param method 被拦截的方法（需要增强的方法）
     * @param args   方法入参
     * @param proxy  用于调用原始方法
     * @return
     * @throws Throwable
     */
    public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
        //调用方法之前，我们可以添加自己的操作
        System.out.println("before method " + method.getName());
        Object object = proxy.invokeSuper(obj, args);
        //调用方法之后，我们同样可以添加自己的操作
        System.out.println("after method " + method.getName());
        return object;
    }
}
