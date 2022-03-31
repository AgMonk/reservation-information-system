package com.gin.reservationinformationsystem.sys.user_info.requestObject;

import com.baomidou.mybatisplus.annotation.TableField;
import com.gin.reservationinformationsystem.sys.params_validation.annotation.NotEmpty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 用来管理角色的用户对象
 * @author bx002
 */
@Data
@ApiModel("管理用户角色")
public class UserForManageRole implements Serializable {
    @NotEmpty(value = "uuid")
    @ApiModelProperty(value = "用户uuid", required = true)
    String uuid;

    @TableField(exist = false)
            @ApiModelProperty(value = "角色uuid",required = true)
    List<String> roleUuid;

}