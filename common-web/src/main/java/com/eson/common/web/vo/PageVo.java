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
    private int totalPage;

    private List<T> list;

    public static <T> PageVo<T> of(PageQuery pageQuery) {
        PageVo<T> emptyPage = new PageVo<>();
        emptyPage.setPageNum(pageQuery.getPageNum());
        emptyPage.setPageSize(pageQuery.getPageSize());
        emptyPage.setList(Collections.emptyList());

        return emptyPage;
    }

    public static <T> PageVo<T> of(PageQuery pageQuery, int total, List<T> list) {
        PageVo<T> vo = new PageVo<>();
        vo.setTotal(total);

        vo.setPageNum(pageQuery.getPageNum());
        int pageSize = pageQuery.getPageSize();
        vo.setPageSize(pageSize);
        vo.setTotalPage((int) Math.ceil((double) total / pageSize));

        vo.setList(list == null ? Collections.emptyList() : list);

        return vo;
    }
}
