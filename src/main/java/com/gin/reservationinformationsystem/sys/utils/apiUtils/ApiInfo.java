package com.gin.reservationinformationsystem.sys.utils.apiUtils;

import com.alibaba.fastjson.annotation.JSONField;
import com.gin.reservationinformationsystem.sys.utils.StringUtils;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.subject.Subject;

import java.io.Serializable;

/**
 * Api信息
 * @author bx002
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApiInfo implements Serializable {
    String path;

    @JSONField(serialize = false)
    String[] requiresPermissions;

    @JSONField(serialize = false)
    Logical logical;

    String operation;

    public ApiInfo(String path) {
        this.path = path;
    }

    public boolean hasPermission() {
        if (StringUtils.isEmpty(requiresPermissions)) {
            return true;
        }
        final Subject subject = SecurityUtils.getSubject();
        if (logical == Logical.AND) {
            return subject.isPermittedAll(requiresPermissions);
        }
        if (logical == Logical.OR) {
            for (String permission : requiresPermissions) {
                if (subject.isPermitted(permission)) {
                    return true;
                }
            }
        }
        return false;
    }
}
