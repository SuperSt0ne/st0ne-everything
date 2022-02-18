package com.rango.common.dto.excel;


import com.rango.common.dto.excel.base.NoModuleRowDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class ExampleNoModuleRowDTO extends NoModuleRowDTO {
    private static final long serialVersionUID = 4736644330233692539L;

    public static final String HEAD_PROJECT_NAME = "项目名";

    public static final String HEAD_BIZ_NAME = "业务名";

    public static final String HEAD_WORK_NAME = "标题";

    public static final List<String> STAND_HEAD_LIST = new ArrayList<>();

    static {
        STAND_HEAD_LIST.add(HEAD_PROJECT_NAME);
        STAND_HEAD_LIST.add(HEAD_BIZ_NAME);
        STAND_HEAD_LIST.add(HEAD_WORK_NAME);
    }
}
