package com.eson.common.excel.event;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.CollectionUtils;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.metadata.Cell;
import com.alibaba.excel.read.metadata.holder.ReadRowHolder;
import com.eson.common.core.util.ReflectUtils;
import com.eson.common.excel.model.BaseRowModel;

import lombok.Getter;
import lombok.Setter;

/**
 * @author dengxiaolin
 * @since 2021/01/19
 */
@Getter
@Setter
public class BaseExcelListener<T extends BaseRowModel> extends AnalysisEventListener<T> {
    private List<BaseRowModel> rows = new ArrayList<>();

    private Class<T> rowModel;
    private List<String> headList;
    private List<String> modelProperties;

    private String sheetName;

    /**
     * 是否还有下一行
     */
    private boolean hasNext;

    /**
     * 错误信息，方便页面展示
     * key是行数，value是错误信息
     */
    private Map<Integer, String> errorMsgMap = new LinkedHashMap<>();

    /**
     * 解析动态属性， titles比model的属性多
     */
    public BaseExcelListener(Class<T> rowModel, List<String> headList) {
        this.rowModel = rowModel;
        fillModelProperties(rowModel);
        this.headList = headList;
    }

    private void fillModelProperties(Class<T> rowModel) {
        List<String> modelProperties = new ArrayList<>();
        Arrays.stream(rowModel.getDeclaredFields())
                .forEach(field -> {
                    ExcelProperty excelProperty = field.getAnnotation(ExcelProperty.class);
                    if (excelProperty != null) {
                        modelProperties.add(field.getName());
                    }
                });
        this.modelProperties = modelProperties;
    }

    @Override
    public void invokeHeadMap(Map<Integer, String> headMap, AnalysisContext context) {
        setHasNext(validateHeadAndHasNext(headMap, headList));
    }


    /**
     * 验证head
     * 返回true表示继续后面的解析， 返回false表示不继续
     */
    public boolean validateHeadAndHasNext(Map<Integer, String> headMap, List<String> titles) {
        return true;
    }


    @Override
    public void invoke(T row, AnalysisContext analysisContext) {
        // 有动态属性
        boolean hasDynamicProperties = CollectionUtils.isNotEmpty(headList)
                && headList.size() > modelProperties.size();

        if (hasDynamicProperties) {
            Map<String, String> dynamicPropertyMap = new HashMap<>(16);
            row.setDynamicPropertyMap(dynamicPropertyMap);

            ReadRowHolder readRowHolder = (ReadRowHolder) ReflectUtils.getFieldValueByName(analysisContext, "readRowHolder");
            Map<Integer, Cell> cellMap = readRowHolder.getCellMap();
            for (int i = modelProperties.size(); i < headList.size(); i++) {
                Cell cell = cellMap.get(i);
                if (cell != null) {
                    dynamicPropertyMap.put(headList.get(i), cell.toString());
                }
            }
        }

        rows.add(row);
        setHasNext(validateRowAndHasNext(row, rows.size()));
    }


    /**
     * 预留钩子给校验每一行
     * 返回true表示继续下一行
     */
    public boolean validateRowAndHasNext(T row, int rowIndex) {
        return true;
    }


    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }

    @Override
    public boolean hasNext(AnalysisContext context) {
        return this.hasNext;
    }
}
