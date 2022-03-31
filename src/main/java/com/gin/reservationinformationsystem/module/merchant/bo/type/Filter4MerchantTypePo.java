package com.gin.reservationinformationsystem.module.merchant.bo.type;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.gin.reservationinformationsystem.sys.request.IFilter;
import com.gin.reservationinformationsystem.sys.utils.QueryWrapperUtils;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 查询商户类型过滤条件
 * @author bx002
 */
@Data
@ApiModel("查询商户类型过滤条件")
public class Filter4MerchantTypePo implements Serializable, IFilter {
    @ApiModelProperty("名称关键字")
    String name;

    @Override
    public void handleQueryWrapper(QueryWrapper<?> queryWrapper) {
        QueryWrapperUtils.likeIfNotEmpty(queryWrapper, "name", name);
    }
}