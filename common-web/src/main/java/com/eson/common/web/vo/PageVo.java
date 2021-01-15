package com.eson.common.web.vo;

import java.util.Collections;
import java.util.List;

import com.eson.common.web.query.PageQuery;

import lombok.Getter;
import lombok.Setter;

/**
 * @author dengxiaolin
 * @since 2021/01/07
 */
@Getter
@Setter
public class PageVo<T> {

    private int pageNum;


    private int pageSize;


    private int total;


    private int totalNum;


    private List<T> list;

    public static <T> PageVo<T> of(PageQuery pageQuery) {
        PageVo<T> emptyPage = new PageVo<>();
        emptyPage.setPage(pageQuery, 0, Collections.emptyList());
        return emptyPage;
    }


    public void setPage(PageQuery pageQuery, int total) {
        this.total = total;
        this.pageNum = pageQuery.getPageNum();
        this.pageSize = pageQuery.getPageSize();
        this.totalNum = (int) Math.ceil((double) total / pageSize);
    }

    public void setPage(PageQuery pageQuery, int total, List<T> list) {
        this.total = total;
        this.pageNum = pageQuery.getPageNum();
        this.pageSize = pageQuery.getPageSize();
        this.list = list;
        this.totalNum = (int) Math.ceil((double) total / pageSize);
    }
}
