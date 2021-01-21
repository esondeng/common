package com.eson.common.excel.param;

import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

import com.alibaba.excel.write.merge.OnceAbsoluteMergeStrategy;
import com.eson.common.excel.model.BaseRowModel;

import lombok.Getter;
import lombok.Setter;

/**
 * @author dengxiaolin
 * @since 2021/01/21
 */
@Getter
@Setter
public class SheetWriteParam<T extends BaseRowModel> {
    @NotEmpty(message = "head不能为空")
    List<String> headList;

    List<T> dataList;

    List<OnceAbsoluteMergeStrategy> mergeStrategyList;

    @NotBlank(message = "sheet name不能为空")
    String sheetName;
}
