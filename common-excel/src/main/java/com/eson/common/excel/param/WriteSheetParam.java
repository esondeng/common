package com.eson.common.excel.param;

import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

import com.alibaba.excel.write.handler.AbstractSheetWriteHandler;
import com.eson.common.excel.model.BaseRowModel;

import lombok.Getter;
import lombok.Setter;

/**
 * @author dengxiaolin
 * @since 2021/01/21
 */
@Getter
@Setter
public class WriteSheetParam<T extends BaseRowModel> {
    @NotBlank(message = "sheet name不能为空")
    private String sheetName;

    @NotEmpty(message = "head不能为空")
    private List<String> headList;

    private List<T> dataList;

    private List<AbstractSheetWriteHandler> mergeStrategyList;


}
