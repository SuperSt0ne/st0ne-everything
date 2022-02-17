package com.rango.common.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class AppConfDTO implements Serializable {

    private static final long serialVersionUID = 5269485726124393549L;

    /**
     * system
     */
    public static final Integer TYPE_SYSTEM = 1;

    private Long id;

    private String confKey;

    private String confVal;

    private Long userId;

    private String userName;

    private Integer type;
}
