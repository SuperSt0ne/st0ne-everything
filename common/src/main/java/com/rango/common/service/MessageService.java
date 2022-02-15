package com.rango.common.service;

import com.rango.common.dto.MessageDTO;

public interface MessageService {

    MessageDTO getMsg(Long userName);
}
