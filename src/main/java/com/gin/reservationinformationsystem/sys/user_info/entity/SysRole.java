package com.gin.reservationinformationsystem.sys.user_info.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.gin.reservationinformationsystem.sys.request.IFilter;
import com.gitee.sunchenbin.mybatis.actable.annotation.Column;
import com.gitee.sunchenbin.mybatis.actable.annotation.IsKey;
import com.gitee.sunchenbin.mybatis.actable.annotation.TableComment;
import com.gitee.sunchenbin.mybatis.actable.annotation.Unique;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * 角色
 * @author bx002
 */
@Data
@TableName(value = "t_sys_user_info_entity_role", autoResultMap = true)
@TableComment("角色")
@ApiModel("角色")
@NoArgsConstructor
public class SysRole implements Serializable, IFilter {
    public static final String ADMINISTRATOR_ROLE = "超级管理员";
    /**
     * 唯一编号
     */
    @TableId
    @Column(length = 36, isNull = false)
    @IsKey
    @ApiModelProperty(value = "唯一编号",required = true)
    String uuid;
    /**
     * 角色名
     */
    @Column(comment = "角色名称", length = 100, isNull = false)
    @Unique
    @ApiModelProperty(value = "角色名称",required = true)
    String name;

    /**
     * 含有的权限
     */
    @TableField(exist = false)
    @ApiModelProperty(value = "含有权限",required = true)
    List<String> permissions;

    public void createUuid() {
        this.uuid = UUID.randomUUID().toString();
    }

    public void addPermission(String permission){
        this.permissions= this.permissions==null?new ArrayList<>():this.permissions;
        this.permissions.add(permission);
    }

    public SysRole(String name) {
        this.name = name;
        createUuid();
    }

    /**
     * 向QueryWrapper中添加过滤条件
     * @param queryWrapper 查询条件
     */
    @Override
    public void handleQueryWrapper(QueryWrapper<?> queryWrapper) {

    }
}