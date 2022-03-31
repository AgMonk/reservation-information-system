package com.gin.reservationinformationsystem.sys.user_info.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.gitee.sunchenbin.mybatis.actable.annotation.IsKey;
import com.gitee.sunchenbin.mybatis.actable.annotation.TableComment;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import com.baomidou.mybatisplus.annotation.TableId;
import com.gitee.sunchenbin.mybatis.actable.annotation.Column;

import java.io.Serializable;
import java.util.UUID;

/**
 * 角色拥有的权限关联
 * @author bx002
 */
@Data
@TableName(value = "t_sys_user_info_relation_role_permission", autoResultMap = true)
@TableComment("角色拥有的权限关联")
@NoArgsConstructor
@AllArgsConstructor
public class RelationRolePermission implements Serializable {
    @TableId
    @IsKey
    @Column(length = 36, isNull = false)
    String uuid;

    @Column(comment = "角色uuid",length = 36, isNull = false)
    String roleUuid;

    @Column(comment = "权限uuid",length = 36, isNull = false)
    String permissionUuid;


    @TableField(exist = false)
    SysPermission permission;

    public void createUuid() {
        this.uuid = UUID.randomUUID().toString();
    }

    public RelationRolePermission(String roleUuid, String permissionUuid) {
        this.roleUuid = roleUuid;
        this.permissionUuid = permissionUuid;
        this.uuid = UUID.randomUUID().toString();
    }
}