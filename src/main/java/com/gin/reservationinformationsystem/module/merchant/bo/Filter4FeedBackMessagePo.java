package com.gin.reservationinformationsystem.module.merchant.bo;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.gin.reservationinformationsystem.sys.params_validation.annotation.EffectiveValues;
import com.gin.reservationinformationsystem.sys.request.IFilter;
import com.gin.reservationinformationsystem.sys.utils.QueryWrapperUtils;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

import static com.gin.reservationinformationsystem.module.merchant.entity.FeedBackMessagePo.STATUS_NOT_PROCESSED;
import static com.gin.reservationinformationsystem.module.merchant.entity.FeedBackMessagePo.STATUS_PROCESSED;

/**
 * @author bx002
 */
@Data
@ApiModel("查询过滤条件")
public class Filter4FeedBackMessagePo implements Serializable, IFilter {
    @ApiModelProperty("状态")
    @EffectiveValues(prefix = "状态", values = {STATUS_NOT_PROCESSED, STATUS_PROCESSED}, nullable = true)
    String status;

    @Override
    public void handleQueryWrapper(QueryWrapper<?> queryWrapper) {
        QueryWrapperUtils.equalsIfNotEmpty(queryWrapper, "status", status);
    }
}