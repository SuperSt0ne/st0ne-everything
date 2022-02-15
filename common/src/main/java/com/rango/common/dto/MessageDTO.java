package com.rango.common.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MessageDTO implements Serializable {

    private static final long serialVersionUID = 1520852149309183485L;

    public static final Integer NORMAL = 1;

    private Long id;

    private String content;

    private Integer type;

    private Long sendUserId;

    private Date sendTime;

    private String event;

}
