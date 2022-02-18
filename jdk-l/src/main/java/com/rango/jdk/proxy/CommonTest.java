package com.rango.jdk.proxy;

import com.rango.jdk.proxy.factory.CglibProxyFactory;
import com.rango.jdk.proxy.factory.JdkProxyFactory;
import com.rango.jdk.sdk.SmsService;
import com.rango.jdk.sdk.impl.SmsServiceImpl;
import org.junit.Test;

public class CommonTest {

    @Test
    public void test1() {
        SmsService smsService = (SmsService) JdkProxyFactory.getCommProxy(new SmsServiceImpl());
        smsService.send("welcome to Apple.inc", "2380584338@qq.com");

        SmsService smsServiceCglib = (SmsService) CglibProxyFactory.getCommProxy(SmsServiceImpl.class);
        smsServiceCglib.send("welcome to HUAWEI.inc", "2380584338@qq.com");
    }
}
