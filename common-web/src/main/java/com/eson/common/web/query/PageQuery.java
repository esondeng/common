package com.eson.common.web.query;

import lombok.Getter;
import lombok.Setter;

/**
 * @author dengxiaolin
 * @since 2021/01/07
 */
public class PageQuery {
    @Getter
    @Setter
    protected Integer pageNum;

    @Getter
    @Setter
    protected Integer pageSize;

    public void valid() {
        pageNum = pageNum == null || pageNum < 1 ? 1 : pageNum;
        pageSize = pageSize == null || pageSize < 1 ? 20 : pageSize;
    }

    public Integer getOffset() {
        if (null == pageNum || pageSize == null) {
            return null;
        }
        if (pageNum <= 0) {
            pageNum = 1;
        }
        return (pageNum - 1) * pageSize;
    }

}
