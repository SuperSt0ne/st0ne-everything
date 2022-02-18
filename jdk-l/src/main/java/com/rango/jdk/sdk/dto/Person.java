package com.rango.jdk.sdk.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class Person implements Serializable {
    private static final long serialVersionUID = -4007662435399891065L;

    private Long id;

    private String name;

    private Integer age;
}
