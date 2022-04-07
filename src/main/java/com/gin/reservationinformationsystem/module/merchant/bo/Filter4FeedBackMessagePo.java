package com.gin.reservationinformationsystem.module.merchant.bo;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.gin.reservationinformationsystem.sys.request.IFilter;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author bx002
 */
@Data
@ApiModel("查询过滤条件")
public class Filter4FeedBackMessagePo implements Serializable, IFilter {
    @ApiModelProperty("状态")
    String status;

    @Override
    public void handleQueryWrapper(QueryWrapper<?> queryWrapper) {
        queryWrapper.eq("status",status);
    }
}