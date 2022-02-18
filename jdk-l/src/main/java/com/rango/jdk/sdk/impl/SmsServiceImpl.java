package com.rango.jdk.sdk.impl;


import com.rango.jdk.sdk.SmsService;

public class SmsServiceImpl implements SmsService {

    public Boolean send(String content, String address) {
        System.out.printf("send msg:%s, address:%s%n", content, address);
        return Boolean.TRUE;
    }

}
