package com.gin.reservationinformationsystem.module.merchant.bo.tag;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.gin.reservationinformationsystem.sys.request.IFilter;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;

/**
 * 商户标签
 * @author bx002
 */
@Data
@ApiModel("商户标签过滤条件")
public class Filter4MerchantTagPo implements Serializable, IFilter {
    @Override
    public void handleQueryWrapper(QueryWrapper<?> queryWrapper) {

    }
}