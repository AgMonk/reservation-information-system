package com.gin.reservationinformationsystem.module.merchant.bo;

import com.gin.reservationinformationsystem.module.merchant.entity.FeedBackMessagePo;
import com.gin.reservationinformationsystem.sys.params_validation.annotation.NotEmpty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;
import java.time.ZonedDateTime;

import static com.gin.reservationinformationsystem.module.merchant.entity.FeedBackMessagePo.STATUS_NOT_PROCESSED;

/**
 * 创建反馈信息
 * @author bx002
 */
@Data
@ApiModel("创建反馈信息")
public class FeedBackMessagePo4Create implements Serializable {
    @ApiModelProperty(value = "商户唯一编号", required = true)
    @NotEmpty("商户唯一编号")
    String merchantUuid;

    @ApiModelProperty(value = "反馈信息", required = true)
    @NotEmpty("反馈信息")
    String content;

    public FeedBackMessagePo toFeedBackMessagePo() {
        final FeedBackMessagePo po = new FeedBackMessagePo();
        BeanUtils.copyProperties(this, po);
        po.createUuid();
        po.setStatus(STATUS_NOT_PROCESSED);
        po.setTimestampCreated(ZonedDateTime.now().toEpochSecond());
        return po;
    }
}