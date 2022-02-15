package com.rango.consumer.controller;

import com.rango.common.dto.MessageDTO;
import com.rango.common.service.MessageService;
import com.rango.basic.result.RangoResult;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CommonController {

    @DubboReference
    private MessageService messageService;

    @GetMapping("/getUser")
    public RangoResult<MessageDTO> user(Long userId) {
        RangoResult<MessageDTO> result = new RangoResult<>();
        result.setData(messageService.getMsg(userId));
        result.setStatus(Boolean.TRUE);
        return result;
    }
}
