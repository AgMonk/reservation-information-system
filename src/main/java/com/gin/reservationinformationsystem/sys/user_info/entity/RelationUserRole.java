package com.gin.reservationinformationsystem.sys.user_info.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.gitee.sunchenbin.mybatis.actable.annotation.Column;
import com.gitee.sunchenbin.mybatis.actable.annotation.IsKey;
import com.gitee.sunchenbin.mybatis.actable.annotation.TableComment;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;
import java.util.UUID;

/**
 * 用户拥有的角色
 * @author bx002
 */
@Data
@TableName(value = "t_sys_user_info_relation_user_role", autoResultMap = true)
@TableComment("用户拥有的角色")
@NoArgsConstructor
@AllArgsConstructor
public class RelationUserRole implements Serializable {
    @TableId
    @Column(length = 36, isNull = false)
    @IsKey
    String uuid;


    @Column(comment = "用户uuid", length = 36, isNull = false)
    String userUuid;

    @Column(comment = "角色uuid", length = 36, isNull = false)
    String roleUuid;

    @TableField(exist = false)
    SysRole role;

    public void setRole(SysRole role) {
        this.role = new SysRole();
        BeanUtils.copyProperties(role,this.role);
    }

    public RelationUserRole(String userUuid, String roleUuid) {
        this.userUuid = userUuid;
        this.roleUuid = roleUuid;
        this.uuid = UUID.randomUUID().toString();
    }

    public void createUuid() {
        this.uuid = UUID.randomUUID().toString();
    }
}