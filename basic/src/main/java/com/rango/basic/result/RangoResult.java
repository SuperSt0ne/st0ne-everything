package com.rango.basic.result;

import lombok.Data;

import java.io.Serializable;

@Data
public class RangoResult<T> implements Serializable {

    private static final long serialVersionUID = -3776238194059561696L;

    private T data;

    private Boolean status;

    private String errorMsg;

    public void setData(T data) {
        this.status = Boolean.TRUE;
        this.data = data;
    }

    public void setErrorMsg(String errorMsg) {
        this.status = Boolean.FALSE;
        this.errorMsg = errorMsg;
    }
}
