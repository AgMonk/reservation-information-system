package com.gin.reservationinformationsystem.sys.user_info.requestObject;

import com.gin.reservationinformationsystem.sys.params_validation.annotation.NotEmpty;
import com.gin.reservationinformationsystem.sys.params_validation.annotation.Password;
import com.gin.reservationinformationsystem.sys.params_validation.annotation.Phone;
import com.gin.reservationinformationsystem.sys.params_validation.annotation.Username;
import com.gin.reservationinformationsystem.sys.user_info.entity.SysUser;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import java.io.Serializable;

/**
 * 注册用的用户对象
 * @author bx002
 */
@Data
@ApiModel("注册新用户")
public class UserForReg implements Serializable {
    /**
     * 用户名
     */
    @ApiModelProperty(value = "用户名", required = true)
    @Username
    String username;
    /**
     * 密码
     */
    @ApiModelProperty(value = "密码", required = true)
    @Password
    String password;
    /**
     * 电话
     */
    @Phone(nullable = true)
    @ApiModelProperty(value = "电话")
    String phone;
    /**
     * 姓名
     */
    @NotEmpty("姓名")
    @Length(min = 2, message = "姓名最少2个字")
    @ApiModelProperty(value = "姓名", required = true)
    String name;

    public SysUser toSysUser() {
        SysUser user = new SysUser();
        user.setUsername(username);
        user.setPassword(password);
        user.setPhone(phone);
        user.setName(name);
        user.createUuid();
        return user;
    }

}