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
 * 用户的查询过滤条件
 * @author bx002
 */
@Data
@ApiModel("查询用户时的过滤条件")
public class FilterForSysUser implements Serializable, IFilter {
    @ApiModelProperty("姓名")
    String name;
    @ApiModelProperty("持有的角色")
    List<String> roleUuid;

    /**
     * 向QueryWrapper中添加过滤条件
     * @param queryWrapper 查询条件
     */
    @Override
    public void handleQueryWrapper(QueryWrapper<?> queryWrapper) {
        likeIfNotEmpty(queryWrapper, "name", this.name);

        if (!StringUtils.isEmpty(roleUuid)) {
            String tableName = RelationUserRole.class.getAnnotation(TableName.class).value();
            String sql = String.format("select user_uuid from %s where role_uuid in ('%s')"
                    , tableName, String.join("','", roleUuid));

            queryWrapper.inSql("uuid", sql);
        }
    }

}
