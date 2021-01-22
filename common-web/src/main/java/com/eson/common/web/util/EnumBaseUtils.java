package com.eson.common.web.util;

import java.util.EnumSet;
import java.util.List;

import com.eson.common.core.enums.EnumBase;
import com.eson.common.core.util.Funs;
import com.eson.common.web.vo.SelectVo;

/**
 * @author dengxiaolin
 * @since 2021/01/22
 */
public class EnumBaseUtils {

    public <T extends Enum<T> & EnumBase> List<SelectVo> buildSelectVos(Class<T> clazz) {
        return Funs.map(EnumSet.allOf(clazz), t -> SelectVo.of(t.id(), t.message()));
    }
}
