package com.rango.common.service;

import com.rango.common.dto.Message;

public interface MessageService {

    Message getMsg(Long userName);
}
