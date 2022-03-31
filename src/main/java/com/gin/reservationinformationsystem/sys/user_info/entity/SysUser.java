package com.gin.reservationinformationsystem.sys.user_info.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.gin.reservationinformationsystem.sys.params_validation.annotation.NotEmpty;
import com.gin.reservationinformationsystem.sys.utils.JsonUtil;
import com.gitee.sunchenbin.mybatis.actable.annotation.Column;
import com.gitee.sunchenbin.mybatis.actable.annotation.IsKey;
import com.gitee.sunchenbin.mybatis.actable.annotation.TableComment;
import com.gitee.sunchenbin.mybatis.actable.annotation.Unique;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * 用户
 * @author bx002
 */
@Data
@TableName(value = "t_sys_user_info_entity_user", autoResultMap = true)
@TableComment("用户")
@ApiModel("用户")
public class SysUser implements Serializable {
    public static final String ADMINISTRATOR_USER = "administrator";

    /**
     * 唯一编号
     */
    @TableId
    @Column(length = 36, isNull = false)
    @IsKey
    @ApiModelProperty("唯一编号")
    String uuid;

    @JSONField(serialize = false)
    @Column(comment = "盐", isNull = false, length = 20)
    @ApiModelProperty(hidden = true)
    String salt;
    /**
     * 注册时间
     */
    @Column(comment = "注册时间", isNull = false)
    @ApiModelProperty("注册时间")
    Long timestampReg;
    /**
     * 可用状态
     */
    @Column(comment = "可用状态", isNull = false,defaultValue = "1")
    @ApiModelProperty("可用状态")
    Boolean available;
    /**
     * 用户名
     */
    @Column(comment = "用户名", isNull = false)
    @Unique
    @ApiModelProperty("用户名")
    String username;

    @Column(comment = "密码", isNull = false)
    @JSONField(serialize = false)
    @ApiModelProperty(hidden = true)
    String password;
    /**
     * 电话
     */
    @Column(comment = "电话", length = 60)
    @TableField(updateStrategy = FieldStrategy.IGNORED)
    @ApiModelProperty("电话")
    String phone;

    /**
     * 姓名
     */
    @Column(comment = "姓名", length = 60, isNull = false)
    @NotEmpty(value = "姓名")
    @ApiModelProperty("姓名")
    String name;

    /**
     * 拥有的角色
     */
    @TableField(exist = false, updateStrategy = FieldStrategy.IGNORED)
    @ApiModelProperty("拥有的角色")
    List<SysRole> roles;

    public void setRoles(List<SysRole> roles) {
        this.roles = JsonUtil.copy(roles, SysRole.class);
    }

    public void createUuid() {
        this.uuid = UUID.randomUUID().toString();
    }

    public boolean hasRoleByName(String name) {
        if (this.roles == null) {
            return false;
        }
        return this.roles.stream().map(SysRole::getName).collect(Collectors.toList()).contains(name);
    }

}