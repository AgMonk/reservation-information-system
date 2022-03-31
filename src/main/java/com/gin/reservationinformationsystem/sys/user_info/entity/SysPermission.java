package com.gin.reservationinformationsystem.sys.user_info.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.gin.reservationinformationsystem.sys.request.IFilter;
import com.gin.reservationinformationsystem.sys.utils.StringUtils;
import com.gitee.sunchenbin.mybatis.actable.annotation.Column;
import com.gitee.sunchenbin.mybatis.actable.annotation.IsKey;
import com.gitee.sunchenbin.mybatis.actable.annotation.TableComment;
import com.github.xiaoymin.knife4j.annotations.Ignore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

import static com.gin.reservationinformationsystem.sys.exception.BusinessExceptionEnum.PERMISSION_VALUE_NOT_NULL;

/**
 * 权限
 * @author bx002
 */
@Data
@NoArgsConstructor
@TableName(value = "t_sys_user_info_entity_permission", autoResultMap = true)
@TableComment("权限")
@ApiModel("权限")
@AllArgsConstructor
public class SysPermission implements Serializable, IFilter {
    public static final String ADMINISTRATOR_PERMISSION_STRING = "*:*:*";
    public static final SysPermission ADMINISTRATOR_PERMISSION = create(ADMINISTRATOR_PERMISSION_STRING, "超级管理员权限");


    @TableId
    @Column(length = 36, isNull = false)
    @IsKey
    @ApiModelProperty(value = "唯一编号(只读)",accessMode = ApiModelProperty.AccessMode.READ_ONLY)
    String uuid;

    @Column(comment = "命名空间", length = 20, isNull = false)
    @ApiModelProperty(value = "命名空间(操作的对象类型)",required = true)
    String namespace;

    @Column(comment = "操作", length = 20, isNull = false)
    @ApiModelProperty(value = "操作",required = true)
    String action;

    @Column(comment = "对象", length = 20, isNull = false)
    @ApiModelProperty(value = "被操作对象",required = true)
    String target;

    @Column(comment = "备注", length = 1000)
    @ApiModelProperty("备注")
    String remark;

    public String getUuid() {
        return String.format("%s:%s:%s", namespace, action, target);
    }


    @SuppressWarnings("AliControlFlowStatementWithoutBraces")
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SysPermission that = (SysPermission) o;

        if (!getUuid().equals(that.getUuid())) return false;
        if (!getNamespace().equals(that.getNamespace())) return false;
        if (!getAction().equals(that.getAction())) return false;
        return getTarget().equals(that.getTarget());
    }

    @Override
    public int hashCode() {
        int result = getUuid().hashCode();
        result = 31 * result + getNamespace().hashCode();
        result = 31 * result + getAction().hashCode();
        result = 31 * result + getTarget().hashCode();
        return result;
    }

    public SysPermission(String namespace, String action, String target) {
        PERMISSION_VALUE_NOT_NULL.assertNotEmpty(namespace);
        PERMISSION_VALUE_NOT_NULL.assertNotEmpty(action);
        PERMISSION_VALUE_NOT_NULL.assertNotEmpty(target);
        this.namespace = namespace;
        this.action = action;
        this.target = target;
    }

    /**
     * 权限不为空
     * @return 布尔
     */
    public boolean validate() {
        return !StringUtils.isEmpty(namespace) && !StringUtils.isEmpty(action) && !StringUtils.isEmpty(target);
    }

    public static SysPermission parse(String string) {
        if (string == null || "".equals(string.trim())) {
            return null;
        }
        String[] s = string.split(":");
        return new SysPermission(s[0], s[1], s[2]);
    }

    public static SysPermission create(String string, String remark) {
        SysPermission permission = parse(string);
        permission.setRemark(remark);
        return permission;
    }

    /**
     * 向QueryWrapper中添加过滤条件
     * @param queryWrapper 查询条件
     */
    @Override
    public void handleQueryWrapper(QueryWrapper<?> queryWrapper) {

    }
}