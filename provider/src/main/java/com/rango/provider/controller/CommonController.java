package com.rango.provider.controller;

import com.rango.basic.result.RangoResult;
import com.rango.common.dto.MessageDTO;
import com.rango.common.lock.DistributeLock;
import com.rango.common.redis.RangoRedisService;
import com.rango.common.service.MessageService;
import com.rango.common.wheel.MessageCenter;
import lombok.extern.log4j.Log4j2;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@Log4j2
@RestController
public class CommonController {

    @DubboReference
    private MessageService messageService;

    @Autowired
    private DistributeLock distributeLock;

    @Autowired
    private MessageCenter zkMessageCenter;

    @Autowired
    private RangoRedisService rangoRedisService;

    @GetMapping("/getUser")
    public RangoResult<MessageDTO> user(Long userId) {
        RangoResult<MessageDTO> result = new RangoResult<>();
        result.setData(messageService.getMsg(userId));
        result.setStatus(Boolean.TRUE);
        return result;
    }

    @GetMapping("/getLock/{path}")
    public RangoResult<Boolean> getLock(@PathVariable("path") String path) {
        RangoResult<Boolean> result = new RangoResult<>();
        distributeLock.acquireLock(path);
        System.out.println("provider-1 get zk lock");
        System.out.println("wait");
        distributeLock.releaseLock(path);
        System.out.println("provider-1 remove zk lock");
        result.setStatus(Boolean.TRUE);
        return result;
    }

    @GetMapping("/getLock1/{path}")
    public RangoResult<Boolean> getLock1(@PathVariable("path") String path) {
        RangoResult<Boolean> result = new RangoResult<>();
        distributeLock.acquireLock(path);
        System.out.println("provider-2 get zk lock");
        System.out.println("wait");
        distributeLock.releaseLock(path);
        System.out.println("provider-2 remove zk lock");
        result.setStatus(Boolean.TRUE);
        return result;
    }

    @GetMapping("/sendMsg/{path}")
    public RangoResult<Boolean> sendMsg(@PathVariable("path") String path) {
        RangoResult<Boolean> result = new RangoResult<>();
        MessageDTO message = MessageDTO.builder()
                .id(10L)
                .content("hello world")
                .sendUserId(1300197L)
                .sendTime(new Date())
                .event(path)
                .type(1)
                .build();
        zkMessageCenter.sendMessage(path, message);
        System.out.println(path);
        result.setStatus(Boolean.TRUE);
        return result;
    }

    @GetMapping("/redis/{key}/{val}")
    public RangoResult<Boolean> setRedis(@PathVariable("key") String key, @PathVariable("val") String val) {
        RangoResult<Boolean> result = new RangoResult<>();
        result.setStatus(rangoRedisService.set(key, val));
        log.error("controller do biz");
        return result;
    }

    @GetMapping("/redis")
    public RangoResult<Boolean> redis() {
        RangoResult<Boolean> result = new RangoResult<>();
        log.error("完成业务");
        result.setStatus(Boolean.TRUE);
        return result;
    }
}
