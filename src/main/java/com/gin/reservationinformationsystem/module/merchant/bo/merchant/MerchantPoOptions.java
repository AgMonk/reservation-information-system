package com.gin.reservationinformationsystem.module.merchant.bo.merchant;

import com.gin.reservationinformationsystem.sys.bo.FilterOption;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 商户过滤选项
 * @author bx002
 */
@Data
@ApiModel("商户过滤选项")
public class MerchantPoOptions implements Serializable {
    @ApiModelProperty(value = "商户类型")
    List<FilterOption> types;

    @ApiModelProperty(value = "商户类型")
    List<String> area;

}