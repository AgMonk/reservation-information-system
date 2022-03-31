package com.gin.reservationinformationsystem.sys.user_info.requestObject;

import com.gin.reservationinformationsystem.sys.params_validation.annotation.NotEmpty;
import com.gin.reservationinformationsystem.sys.params_validation.annotation.Phone;
import com.gin.reservationinformationsystem.sys.user_info.entity.SysUser;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 注册用的用户对象
 * @author bx002
 */
@Data
@ApiModel("用户个人信息修改")
public class UserForEditInfo implements Serializable {
    /**
     * 电话
     */
    @Phone(nullable = true)
    @ApiModelProperty(value = "电话")
    String phone;
    /**
     * 姓名
     */
    @NotEmpty(value = "姓名")
    @ApiModelProperty(value = "姓名", required = true)
    String name;

    public SysUser toSysUser(){
        SysUser user = new SysUser();
        user.setPhone(phone);
        user.setName(name);
        user.createUuid();
        return user;
    }

}