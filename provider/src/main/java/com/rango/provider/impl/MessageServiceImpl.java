package com.rango.provider.impl;

import com.rango.common.dto.Message;
import com.rango.common.service.MessageService;
import org.apache.dubbo.config.annotation.DubboService;

import java.util.Date;

@DubboService
public class MessageServiceImpl implements MessageService {

    @Override
    public Message getMsg(Long userId) {
        return Message.builder()
                .id(1L)
                .content("hello world")
                .type(Message.NORMAL)
                .sendUserId(userId)
                .sendTime(new Date())
                .build();
    }

}
