package com.rango.common.wheel;

import com.rango.common.dto.MessageDTO;

public interface MessageCenter {

    void sendMessage(String event, MessageDTO message);
}
