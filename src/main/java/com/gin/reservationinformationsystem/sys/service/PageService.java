package com.gin.reservationinformationsystem.sys.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.gin.reservationinformationsystem.sys.request.IFilter;
import com.gin.reservationinformationsystem.sys.request.PageParams;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author bx002
 */
@Transactional(rollbackFor = Exception.class)
public interface PageService<T> extends IService<T> {

    /**
     * 根据名称查询
     * @param name 名称
     * @return Page
     */
    default T getByName(String name) {
        return getOne(new QueryWrapper<T>().eq("name", name));
    }

    /**
     * 根据参数，并应用过滤条件进行分页查询
     * @param params 参数及过滤条件
     * @return 查询结果
     */
    default Page<T> pageWithParams(PageParams<? extends IFilter> params) {
        return pageWithParams(params, null);
    }

    /**
     * 根据参数，并应用过滤条件进行分页查询
     * @param params       参数及过滤条件
     * @param queryWrapper queryWrapper
     * @return 查询结果
     */
    default Page<T> pageWithParams(PageParams<? extends IFilter> params, QueryWrapper<T> queryWrapper) {
        queryWrapper = queryWrapper != null ? queryWrapper : new QueryWrapper<>();
        final IFilter filter = params.getFilter();
        if (filter != null) {
            filter.handleQueryWrapper(queryWrapper);
        }
        return page(new Page<>(params.getPage(), params.getSize()), queryWrapper);
    }
}