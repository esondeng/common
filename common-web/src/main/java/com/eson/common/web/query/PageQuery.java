package com.eson.common.web.query;

import lombok.Getter;
import lombok.Setter;

/**
 * @author dengxiaolin
 * @since 2021/01/07
 */
@Getter
@Setter
public class PageQuery {

    protected Integer pageNum;

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
