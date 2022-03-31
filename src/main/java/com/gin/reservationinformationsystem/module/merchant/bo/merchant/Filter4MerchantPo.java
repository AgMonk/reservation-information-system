package com.gin.reservationinformationsystem.module.merchant.bo.merchant;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.gin.reservationinformationsystem.sys.request.IFilter;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;

/**
 * @author bx002
 */
@Data
@ApiModel("查询商户过滤条件")
public class Filter4MerchantPo implements Serializable, IFilter {
    @Override
    public void handleQueryWrapper(QueryWrapper<?> queryWrapper) {

    }
}