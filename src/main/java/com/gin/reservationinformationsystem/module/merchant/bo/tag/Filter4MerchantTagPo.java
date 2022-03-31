package com.gin.reservationinformationsystem.module.merchant.bo.tag;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.gin.reservationinformationsystem.sys.request.IFilter;
import com.gin.reservationinformationsystem.sys.utils.QueryWrapperUtils;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 商户标签
 * @author bx002
 */
@Data
@ApiModel("商户标签过滤条件")
public class Filter4MerchantTagPo implements Serializable, IFilter {
    @ApiModelProperty(value = "名称关键字")
    String name;

    @Override
    public void handleQueryWrapper(QueryWrapper<?> queryWrapper) {
        QueryWrapperUtils.likeIfNotEmpty(queryWrapper, "name",name);
    }
}