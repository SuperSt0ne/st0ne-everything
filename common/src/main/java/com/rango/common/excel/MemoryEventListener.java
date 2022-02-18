package com.rango.common.excel;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.exception.ExcelAnalysisException;
import com.rango.common.dto.excel.base.ExcelRowDTO;
import lombok.Getter;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 基于内存实现的 Excel 事件监听器，最大收集条数限制为 1000 条
 *
 * @param <T>
 */
public class MemoryEventListener<T extends ExcelRowDTO> extends AnalysisEventListener<T> {

    @Getter
    private final Map<Integer, String> headMap = new LinkedHashMap<>();
    @Getter
    private final List<T> rowList = new ArrayList<>();

    @Override
    public void invoke(T row, AnalysisContext context) {
        if (rowList.size() >= 1000) {
            throw new ExcelAnalysisException("暂不支持单次导入超过 1000 条数据");
        }

        row.setRowIndex(context.readRowHolder().getRowIndex() + 1);

        rowList.add(row);
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {

    }

    @Override
    public void invokeHeadMap(Map<Integer, String> headMap, AnalysisContext context) {
        this.headMap.putAll(headMap);
    }

}
