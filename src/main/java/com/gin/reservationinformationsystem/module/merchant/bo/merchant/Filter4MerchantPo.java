package com.gin.reservationinformationsystem.module.merchant.bo.merchant;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.gin.reservationinformationsystem.sys.request.IFilter;
import com.gin.reservationinformationsystem.sys.utils.QueryWrapperUtils;
import com.gin.reservationinformationsystem.sys.utils.StringUtils;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author bx002
 */
@Data
@ApiModel("查询商户过滤条件")
public class Filter4MerchantPo implements Serializable, IFilter {
    @ApiModelProperty(value = "筛选含有指定标签的商户")
    List<String> usedTagsUuid;

    @ApiModelProperty(value = "筛选指定商户类型UUID的商户")
    List<String> types;

    @ApiModelProperty(value = "筛选指定地区的商户")
    List<String> area;

    @Override
    public void handleQueryWrapper(QueryWrapper<?> queryWrapper) {
        if (!StringUtils.isEmpty(usedTagsUuid)) {
            final String inValue = "select merchant_uuid from t_merchant_relation_merchant_tag where tag_uuid in ('%s')";
            final String join = String.join("','", usedTagsUuid);
            queryWrapper.inSql("uuid", String.format(inValue, join));
        }
        QueryWrapperUtils.inIfNotEmpty(queryWrapper,"type_uuid",types);
        QueryWrapperUtils.inIfNotEmpty(queryWrapper,"area",area);
    }
}