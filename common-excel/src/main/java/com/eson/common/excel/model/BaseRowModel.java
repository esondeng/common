package com.eson.common.excel.model;

import java.util.Map;

import com.alibaba.excel.annotation.ExcelIgnore;

import lombok.Getter;
import lombok.Setter;

/**
 * @author dengxiaolin
 * @since 2021/01/20
 */
@Getter
@Setter
public class BaseRowModel {
    @ExcelIgnore
    private Map<String, String> dynamicPropertyMap;
}
