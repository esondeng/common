package com.eson.common.excel.util;

import java.io.InputStream;
import java.lang.reflect.Field;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.EasyExcelFactory;
import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.read.metadata.ReadSheet;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.alibaba.excel.write.builder.ExcelWriterSheetBuilder;
import com.alibaba.excel.write.merge.OnceAbsoluteMergeStrategy;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.eson.common.core.util.Assert;
import com.eson.common.core.util.Funs;
import com.eson.common.core.util.JsonUtils;
import com.eson.common.core.util.ReflectUtils;
import com.eson.common.core.util.ValidatorUtils;
import com.eson.common.excel.event.BaseExcelListener;
import com.eson.common.excel.model.BaseRowModel;
import com.eson.common.excel.param.SheetWriteParam;
import com.eson.common.function.util.ThrowUtils;
import com.google.common.collect.Lists;

/**
 * @author dengxiaolin
 * @since 2021/01/19
 */
public class ExcelUtils {
    private static final String SPECIAL_CHAR = "[^0-9a-zA-Z\u4e00-\u9fa5-_]+";

    /**
     * 同步按模型读
     * 默认读取第一个sheet，Head占一行
     */
    public static <T> List<T> syncRead(InputStream inputStream, Class<T> clazz) {
        return EasyExcelFactory.read(inputStream)
                .sheet(0)
                .headRowNumber(1)
                .head(clazz)
                .doReadSync();
    }

    public static <T extends List<BaseExcelListener<? extends BaseRowModel>>> void asyncRead(InputStream inputStream, T listeners) {
        ExcelReader excelReader = EasyExcel.read(inputStream).build();
        try {
            ReadSheet[] readSheets = new ReadSheet[listeners.size()];
            for (int i = 0; i < listeners.size(); i++) {
                BaseExcelListener listener = listeners.get(i);
                readSheets[i] = EasyExcel.readSheet(i)
                        .head(listener.getRowModel())
                        .registerReadListener(listener).build();
                listener.setSheetName(readSheets[i].getSheetName());
            }

            // 这里注意 一定要把readSheets一起传进去，不然有个问题就是03版的excel 会读取多次，浪费性能
            excelReader.read(readSheets);
        }
        finally {
            excelReader.finish();
        }
    }

    public static <T extends BaseRowModel> void write(HttpServletResponse response, SheetWriteParam<T> writeParam, String fileName) {
        write(response, Arrays.asList(writeParam), fileName);
    }

    public static <T extends List<SheetWriteParam<? extends BaseRowModel>>> void write(HttpServletResponse response, T writeParams, String fileName) {
        Assert.throwIfBlank(fileName, "fileName必传");
        writeParams.forEach(ValidatorUtils::validate);

        List<String> sheetNames = Funs.map(writeParams, SheetWriteParam::getSheetName);
        Set<String> sheetNameSet = new HashSet<>(sheetNames);
        Assert.throwIfTrue(sheetNames.size() != sheetNameSet.size(), "sheetName{}重复", JsonUtils.toJson(sheetNames));

        String name = fileName.replaceAll(SPECIAL_CHAR, "") + ExcelTypeEnum.XLSX.getValue();
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Accept-Ranges", "bytes");
        String fileNameParam = ThrowUtils.execute(() -> URLEncoder.encode(name, "UTF-8"));
        response.setHeader("Content-Disposition", "attachment; filename=" + fileNameParam);

        ServletOutputStream outputStream = ThrowUtils.execute(response::getOutputStream);
        ExcelWriter excelWriter = EasyExcel.write(outputStream).build();

        try {
            for (int i = 0; i < writeParams.size(); i++) {
                SheetWriteParam<? extends BaseRowModel> writeParam = writeParams.get(i);
                String sheetName = writeParam.getSheetName().replaceAll(SPECIAL_CHAR, "");
                List<String> headList = writeParam.getHeadList();
                List<? extends BaseRowModel> dataList = writeParam.getDataList();

                ExcelWriterSheetBuilder writerSheetBuilder = EasyExcel.writerSheet(i, sheetName);
                writerSheetBuilder = writerSheetBuilder.head(Funs.map(headList, head -> Lists.newArrayList(new String[] {head})));

                List<OnceAbsoluteMergeStrategy> mergeStrategyList = writeParam.getMergeStrategyList();
                if (CollectionUtils.isNotEmpty(mergeStrategyList)) {
                    for (OnceAbsoluteMergeStrategy mergeStrategy : mergeStrategyList) {
                        writerSheetBuilder.registerWriteHandler(mergeStrategy);
                    }
                }
                WriteSheet writeSheet = writerSheetBuilder.build();

                if (CollectionUtils.isNotEmpty(dataList)) {
                    excelWriter.write(buildRows(dataList, headList), writeSheet);
                }
            }
        }
        finally {
            //千万别忘记finish 会帮忙关闭流
            excelWriter.finish();
        }
    }

    private static <T extends BaseRowModel> List buildRows(List<T> rows, List<String> headList) {
        return Funs.map(rows, row -> {
            List<Object> list = new ArrayList<>();
            List<Field> excelFields = Funs.filter(
                    Arrays.asList(row.getClass().getDeclaredFields()),
                    field -> field.getAnnotation(ExcelProperty.class) != null);

            excelFields.forEach(field -> {
                list.add(ReflectUtils.getValue(row, field));
                Map<String, String> dynamicPropertyMap = row.getDynamicPropertyMap();
                if (MapUtils.isNotEmpty(dynamicPropertyMap)) {
                    for (int i = excelFields.size(); i < headList.size(); i++) {
                        list.add(dynamicPropertyMap.getOrDefault(headList.get(i), ""));
                    }
                }
            });

            return list;
        });
    }
}


