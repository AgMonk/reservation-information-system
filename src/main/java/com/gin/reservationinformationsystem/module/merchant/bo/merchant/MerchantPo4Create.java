package com.gin.reservationinformationsystem.module.merchant.bo.merchant;

import com.gin.reservationinformationsystem.module.merchant.entity.MerchantPo;
import com.gin.reservationinformationsystem.sys.params_validation.annotation.NotEmpty;
import com.gin.reservationinformationsystem.sys.params_validation.annotation.Phone;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;
import java.time.ZonedDateTime;

/**
 * 创建商户
 * @author bx002
 */
@Data
@ApiModel("创建商户")
public class MerchantPo4Create implements Serializable {
    public static final String API_URL = "https://developers.weixin.qq.com/miniprogram/dev/api/location/wx.getLocation.html";

    @ApiModelProperty(value = "名称", required = true)
    @NotEmpty("名称")
    String name;

    @ApiModelProperty(value = "商户类型uuid", required = true)
    @NotEmpty("商户类型uuid")
    String typeUuid;

    @ApiModelProperty(value = "电话", required = true)
    @Phone
    String phone;

    @ApiModelProperty(value = "地址", required = true)
    @NotEmpty("地址")
    String address;

    @ApiModelProperty(value = "地区", required = true)
    @NotEmpty("地区")
    String area;

    @ApiModelProperty(value = "纬度", notes = "通过该接口获取:" + API_URL)
    Double latitude;

    @ApiModelProperty(value = "经度", notes = "通过该接口获取:" + API_URL)
    Double longitude;

    @ApiModelProperty(value = "介绍", required = true)
    @NotEmpty("介绍")
    String description;

    public MerchantPo toMerchantPo() {
        final MerchantPo po = new MerchantPo();
        BeanUtils.copyProperties(this, po);
        po.setTimestampCreated(ZonedDateTime.now().toEpochSecond());
        po.setStatus(MerchantPo.STATUS_NORMAL);
        return po;
    }
}