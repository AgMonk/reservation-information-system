package com.gin.reservationinformationsystem.sys.user_info.requestObject;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.gin.reservationinformationsystem.sys.request.IFilter;
import com.gin.reservationinformationsystem.sys.user_info.entity.RelationUserRole;
import com.gin.reservationinformationsystem.sys.utils.StringUtils;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

import static com.gin.reservationinformationsystem.sys.utils.QueryWrapperUtils.likeIfNotEmpty;

/**
 * 角色的查询过滤条件
 * @author bx002
 */
@Data
@ApiModel("查询角色时的过滤条件")
public class FilterForSysRole implements Serializable, IFilter {
    @ApiModelProperty(value = "名称关键字")
    String name;

    /**
     * 向QueryWrapper中添加过滤条件
     * @param queryWrapper 查询条件
     */
    @Override
    public void handleQueryWrapper(QueryWrapper<?> queryWrapper) {
        likeIfNotEmpty(queryWrapper, "name", this.name);
    }

}
