package com.rango.common.dto.excel.base;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Map;

@EqualsAndHashCode(callSuper = true)
@Data
public class NoModuleRowDTO extends ExcelRowDTO implements Serializable {
    private static final long serialVersionUID = -4581776680921365804L;

    private Map<Integer, String> rowData;
}
