package com.gin.reservationinformationsystem.module.merchant.bo.merchant_tag;

import com.gin.reservationinformationsystem.module.merchant.entity.RelationMerchantTagPo;
import com.gin.reservationinformationsystem.sys.params_validation.annotation.NotEmpty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;

/**
 * 创建商户的标签
 * @author bx002
 */
@Data
@ApiModel("创建商户的标签")
public class RelationMerchantTagPo4Create implements Serializable {
    @ApiModelProperty(value = "商户UUID", required = true)
    @NotEmpty("商户UUID")
    String merchantUuid;

    @ApiModelProperty(value = "标签UUID", required = true)
    @NotEmpty("标签UUID")
    String tagUuid;

    public RelationMerchantTagPo toRelationMerchantTagPo() {
        final RelationMerchantTagPo po = new RelationMerchantTagPo();
        BeanUtils.copyProperties(this, po);
        po.createUuid();
        return po;
    }
}