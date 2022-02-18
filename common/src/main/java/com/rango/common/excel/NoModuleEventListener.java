package com.rango.common.excel;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.exception.ExcelAnalysisException;
import com.rango.common.dto.excel.base.ExcelRowDTO;
import com.rango.common.dto.excel.base.NoModuleRowDTO;
import lombok.Getter;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 基于内存实现的 Excel 事件监听器，最大收集条数限制为 1000 条
 * <p>
 * 不定长表头 无法数据类型
 *
 * @param <T>
 */
public class NoModuleEventListener<T extends ExcelRowDTO> extends AnalysisEventListener<Map<Integer, String>> {
    @Getter
    private final Map<Integer, String> headMap = new LinkedHashMap<>();
    @Getter
    private final List<NoModuleRowDTO> rowList = new ArrayList<>();

    @Override
    public void invoke(Map<Integer, String> row, AnalysisContext context) {
        if (rowList.size() >= 1000) {
            throw new ExcelAnalysisException("暂不支持单次导入超过 1000 条数据");
        }
        NoModuleRowDTO rowDTO = new NoModuleRowDTO();
        rowDTO.setRowIndex(context.readRowHolder().getRowIndex() + 1);
        rowDTO.setRowData(row);
        rowList.add(rowDTO);
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {

    }

    @Override
    public void invokeHeadMap(Map<Integer, String> headMap, AnalysisContext context) {
        this.headMap.putAll(headMap);
    }
}
