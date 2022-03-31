package com.gin.reservationinformationsystem.module.merchant.bo.type;

import com.gin.reservationinformationsystem.module.merchant.entity.MerchantTypePo;
import com.gin.reservationinformationsystem.sys.params_validation.annotation.NotEmpty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;
import java.util.UUID;

/**
 * 修改商户类型
 * @author bx002
 */
@Data
@ApiModel("修改商户类型")
public class MerchantType4Update implements Serializable {
    @ApiModelProperty(value = "唯一编号", required = true)
    @NotEmpty
    String uuid;

    @ApiModelProperty(value = "名称", required = true)
    @NotEmpty
    String name;

    public void createUuid() {
        this.uuid = UUID.randomUUID().toString();
    }

    public MerchantTypePo toMerchantTypePo(){
        final MerchantTypePo po = new MerchantTypePo();
        BeanUtils.copyProperties(this,po);
        return po;
    }
}