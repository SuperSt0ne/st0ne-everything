package com.rango.provider.controller;

import com.rango.common.dto.Message;
import com.rango.common.dto.RangoResult;
import com.rango.common.service.MessageService;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CommonController {

    @DubboReference
    private MessageService messageService;

    @GetMapping("/getUser")
    public RangoResult<Message> user(Long userId) {
        RangoResult<Message> result = new RangoResult<>();
        result.setData(messageService.getMsg(userId));
        result.setStatus(Boolean.TRUE);
        return result;
    }
}
