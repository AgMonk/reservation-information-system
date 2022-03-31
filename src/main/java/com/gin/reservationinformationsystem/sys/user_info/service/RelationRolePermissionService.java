package com.gin.reservationinformationsystem.sys.user_info.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.gin.reservationinformationsystem.sys.user_info.entity.RelationRolePermission;
import com.gin.reservationinformationsystem.sys.user_info.entity.SysRole;
import com.gin.reservationinformationsystem.sys.utils.StringUtils;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 角色与权限的关联管理：增、删、查
 * @author bx002
 */
@Transactional(rollbackFor = Exception.class)
public interface RelationRolePermissionService extends IService<RelationRolePermission> {
    /**
     * 根据角色id 删除该角色下的所有权限
     * @param roleUuid 角色id
     */
    default void deleteByRoleId(String roleUuid) {
        QueryWrapper<RelationRolePermission> qw = new QueryWrapper<>();
        qw.eq("role_uuid", roleUuid);
        remove(qw);
    }

    /**
     * 保存角色的权限
     * @param roleUuid    角色uuid
     * @param permissions 权限字符串
     */
    default void saveByRoleId(String roleUuid, List<String> permissions) {
        ArrayList<RelationRolePermission> list = new ArrayList<>();
        permissions.forEach(p -> list.add(new RelationRolePermission(roleUuid, p)));
        saveBatch(list);
    }

    /**
     * 更新角色的权限
     * @param roleUuid    角色uuid
     * @param permissions 权限字符串
     */
    default void savePermissions(String roleUuid, List<String> permissions){
        deleteByRoleId(roleUuid);
        saveByRoleId(roleUuid,permissions);
    }

    /**
     * 根据角色uuid 查询角色到权限的关联关系
     * @param roleUuid 角色uuid
     * @return 查询角色到权限的关联关系
     */
    default List<RelationRolePermission> listByRoleId(Collection<String> roleUuid) {
        if (StringUtils.isEmpty(roleUuid)) {
            return new ArrayList<>();
        }
        QueryWrapper<RelationRolePermission> qw = new QueryWrapper<>();
        qw.in("role_uuid", roleUuid);
        return list(qw);
    }

    /**
     * 根据角色uuid 查询角色到权限的关联关系
     * @param roleUuid 角色uuid
     * @return 查询角色到权限的关联关系
     */
    default Map<String, List<String>> mapByRoleId(Collection<String> roleUuid) {
        if (StringUtils.isEmpty(roleUuid)) {
            return new HashMap<>();
        }
        Map<String, List<RelationRolePermission>> map = listByRoleId(roleUuid).stream().collect(Collectors.groupingBy(RelationRolePermission::getRoleUuid));

        HashMap<String, List<String>> hashMap = new HashMap<>(map.size());
        map.forEach((k, v) -> hashMap.put(k, v.stream().map(RelationRolePermission::getPermissionUuid).sorted().collect(Collectors.toList())));

        return hashMap;
    }

    /**
     * 根据权限uuid移除关联
     * @param permissionUuid 权限uuid
     */
    default void removeByPermissionUuid(String permissionUuid){
        QueryWrapper<RelationRolePermission> qw = new QueryWrapper<>();
        qw.eq("permission_uuid",permissionUuid);
        remove(qw);
    }

    /**
     * 向角色合集里填充权限信息
     * @param roles 角色
     * @return 角色所含的所有权限
     */
    default List<String> fillPermissions(Collection<SysRole> roles) {
        if (StringUtils.isEmpty(roles)) {
            return new ArrayList<>();
        }
        List<String> roleUuid = roles.stream().map(SysRole::getUuid).distinct().collect(Collectors.toList());
        Map<String, List<String>> mapByRoleId = mapByRoleId(roleUuid);
        roles.forEach(role -> role.setPermissions(mapByRoleId.getOrDefault(role.getUuid(), new ArrayList<>())));
        return mapByRoleId.values().stream().flatMap(Collection::stream).distinct().collect(Collectors.toList());
    }
}