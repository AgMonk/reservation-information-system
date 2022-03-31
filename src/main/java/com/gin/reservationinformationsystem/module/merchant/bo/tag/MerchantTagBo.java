package com.gin.reservationinformationsystem.module.merchant.bo.tag;

import com.gin.reservationinformationsystem.sys.params_validation.annotation.NotEmpty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 商户名下的标签
 * @author bx002
 */
@Data
@ApiModel("商户名下的标签")
@NoArgsConstructor
@AllArgsConstructor
public class MerchantTagBo implements Serializable {
    @ApiModelProperty(value = "唯一编号", required = true)
    @NotEmpty("唯一编号")
    String uuid;

    @ApiModelProperty(value = "名称", required = true)
    @NotEmpty("名称")
    String name;

}