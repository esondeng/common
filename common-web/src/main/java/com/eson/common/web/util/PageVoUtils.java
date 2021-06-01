package com.eson.common.web.util;

import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

import com.eson.common.core.util.Funs;
import com.eson.common.web.query.PageQuery;
import com.eson.common.web.vo.PageVo;

/**
 * @author dengxiaolin
 * @since 2021/06/01
 */
public class PageVoUtils {
    /**
     * @param <T> 通常是数据库返回Po
     * @param <R> 页面展示Vo
     */
    public static <T, R> PageVo<R> buildPageVo(PageQuery pageQuery,
                                               Supplier<Integer> totalSupplier,
                                               Supplier<List<T>> listSupplier,
                                               Function<T, R> mapper) {
        int total = totalSupplier.get();
        if (total == 0) {
            return PageVo.of(pageQuery);
        }

        List<T> list = listSupplier.get();
        return PageVo.of(pageQuery, total, Funs.map(list, mapper));
    }
}
