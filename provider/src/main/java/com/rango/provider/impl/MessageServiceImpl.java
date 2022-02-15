package com.rango.provider.impl;

import com.rango.common.dto.MessageDTO;
import com.rango.common.service.MessageService;
import org.apache.dubbo.config.annotation.DubboService;

import java.util.Date;

@DubboService
public class MessageServiceImpl implements MessageService {

    @Override
    public MessageDTO getMsg(Long userId) {
        return MessageDTO.builder()
                .id(1L)
                .content("hello world")
                .type(MessageDTO.NORMAL)
                .sendUserId(userId)
                .sendTime(new Date())
                .build();
    }

}
