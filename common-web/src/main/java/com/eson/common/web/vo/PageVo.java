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


    public void setPage(int total, PageQuery pageQuery) {
        this.total = total;
        this.pageNum = pageQuery.getPageNum();
        this.pageSize = pageQuery.getPageSize();
        this.totalNum = (int) Math.ceil((double) total / pageSize);
    }

    public void setPage(int total, PageQuery pageQuery, List<T> list) {
        this.total = total;
        this.pageNum = pageQuery.getPageNum();
        this.pageSize = pageQuery.getPageSize();
        this.list = list;
        this.totalNum = (int) Math.ceil((double) total / pageSize);
    }

    public static <M> PageVo<M> of() {
        return new PageVo<>();
    }

    public static <M> PageVo<M> empty(PageQuery pageQuery) {
        PageVo<M> emptyPage = new PageVo<>();
        emptyPage.setPage(0, pageQuery, Collections.emptyList());
        return emptyPage;
    }
}
