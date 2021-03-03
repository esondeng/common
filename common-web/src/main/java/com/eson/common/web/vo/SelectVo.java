package com.eson.common.web.vo;

import com.eson.common.core.enums.EnumBase;

import lombok.Getter;
import lombok.Setter;

/**
 * @author dengxiaolin
 * @since 2021/01/22
 */
@Getter
@Setter
public class SelectVo {
    private int id;
    private String name;

    public static SelectVo of(EnumBase enumBase) {
        return of(enumBase.id(), enumBase.message());
    }

    public static SelectVo of(int id, String name) {
        SelectVo vo = new SelectVo();
        vo.setId(id);
        vo.setName(name);

        return vo;
    }
}
