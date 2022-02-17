package com.rango.provider.dataObject;

import lombok.Data;

import java.io.Serializable;

@Data
public class AppConfDO implements Serializable {

    private static final long serialVersionUID = -1907478001836644727L;

    private Long id;

    private String confKey;

    private String confVal;

    private Long userId;

    private String userName;

    private Integer type;
}
