package com.rango.consumer.controller;

import com.rango.basic.annotation.CatchMessage;
import com.rango.common.dto.MessageDTO;
import com.rango.common.lock.DistributeLock;
import com.rango.common.service.MessageService;
import com.rango.basic.result.RangoResult;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CommonController {

    @DubboReference
    private MessageService messageService;

    @Autowired
    private DistributeLock distributeLock;

    @GetMapping("/getUser")
    public RangoResult<MessageDTO> user(Long userId) {
        RangoResult<MessageDTO> result = new RangoResult<>();
        result.setData(messageService.getMsg(userId));
        result.setStatus(Boolean.TRUE);
        return result;
    }

    @CatchMessage(event = "kill")
    @GetMapping("/getLock/{path}")
    public RangoResult<Boolean> getLock(@PathVariable("path") String path) {
        RangoResult<Boolean> result = new RangoResult<>();
        distributeLock.acquireLock(path);
        System.out.println("consumer get zk lock");
        System.out.println("wait");
        distributeLock.releaseLock(path);
        System.out.println("consumer remove zk lock");
        result.setStatus(Boolean.TRUE);
        return result;
    }
}
