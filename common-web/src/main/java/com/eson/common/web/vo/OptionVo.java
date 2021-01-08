package com.eson.common.web.vo;

import lombok.Getter;
import lombok.Setter;

/**
 * @author dengxiaolin
 * @since 2021/01/07
 */
@Getter
@Setter
public class OptionVo {

    private Integer id;

    private String name;

    private boolean selectable;

    private boolean selected;

    public static OptionVo of(int id, String name, boolean selectable, boolean selected) {
        OptionVo vo = new OptionVo();
        vo.setId(id);
        vo.setName(name);
        vo.setSelectable(selectable);
        vo.setSelected(selected);

        return vo;
    }
}
