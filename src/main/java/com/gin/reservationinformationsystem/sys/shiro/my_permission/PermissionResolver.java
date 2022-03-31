package com.gin.reservationinformationsystem.sys.shiro.my_permission;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

/**
 * 资源权限解析器
 * @author bx002
 */
public interface PermissionResolver {

    /**
     * 批量解析资源
     */
    static List<String> resolve(List<PermissionResolver> list) {
        return Optional.ofNullable(list).map(obj -> obj.stream().map(PermissionResolver::resolve).collect(toList()))
                .orElse(Collections.emptyList());
    }

    /**
     * 解析资源 (生成资源的唯一编号 , 可以直接用资源的uuid , 或者再加上它的所属 uuid)
     * @return 资源的权限表示字符串
     */
    String resolve();
}
