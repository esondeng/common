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

    public void validate() {
        pageNum = pageNum == null || pageNum < 1 ? 1 : pageNum;
        pageSize = pageSize == null || pageSize < 1 ? 20 : pageSize;
    }

    /**
     * 请求之前保证先validate
     */
    public Integer getOffset() {
        return (pageNum - 1) * pageSize;
    }

}
