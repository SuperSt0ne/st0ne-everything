package com.rango.common.dto.excel.base;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@EqualsAndHashCode(exclude = "rowIndex")
@Data
public class ExcelRowDTO implements Serializable {
    private static final long serialVersionUID = 7589430444902970597L;

    protected Integer rowIndex;

}
