package com.gin.reservationinformationsystem.sys.user_info.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.gin.reservationinformationsystem.sys.user_info.entity.RelationUserRole;
import com.gin.reservationinformationsystem.sys.user_info.entity.SysRole;
import com.gin.reservationinformationsystem.sys.user_info.entity.SysUser;
import com.gin.reservationinformationsystem.sys.utils.StringUtils;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 用户与角色的关联管理：增、删、查
 * @author bx002
 */
@Transactional(rollbackFor = Exception.class)
public interface RelationUserRoleService extends IService<RelationUserRole> {

    /**
     * 根据用户id 删除该用户下的所有角色
     * @param userUuid 用户id
     */
    default void deleteByUserId(String userUuid) {
        QueryWrapper<RelationUserRole> qw = new QueryWrapper<>();
        qw.eq("user_uuid", userUuid);
        remove(qw);
    }

    /**
     * 保存用户的角色
     * @param userUuid 用户uuid
     * @param roles    角色字符串
     */
    default void saveByUserId(String userUuid, Collection<String> roles) {
        ArrayList<RelationUserRole> list = new ArrayList<>();
        roles.forEach(p -> list.add(new RelationUserRole(userUuid, p)));
        saveBatch(list);
    }

    /**
     * 更新用户的角色
     * @param userUuid 用户uuid
     * @param roles    角色字符串
     */
    default void saveRole(String userUuid, Collection<String> roles) {
        deleteByUserId(userUuid);
        saveByUserId(userUuid, roles);
    }

    default void addRole(String userUuid, Collection<String> roles) {
        Set<String> set = listByUserId(Collections.singleton(userUuid)).stream().map(RelationUserRole::getRoleUuid).collect(Collectors.toSet());
        set.addAll(roles);
        saveRole(userUuid, set);
    }

    default void removeRole(String userUuid, Collection<String> roles) {
        Set<String> set = listByUserId(Collections.singleton(userUuid)).stream().map(RelationUserRole::getRoleUuid).collect(Collectors.toSet());
        set.removeAll(roles);
        saveRole(userUuid, set);
    }


    /**
     * 根据用户uuid 查询用户到角色的关联关系
     * @param userUuid 用户uuid
     * @return 查询用户到角色的关联关系
     */
    default List<RelationUserRole> listByUserId(Collection<String> userUuid) {
        if (StringUtils.isEmpty(userUuid)) {
            return new ArrayList<>();
        }
        QueryWrapper<RelationUserRole> qw = new QueryWrapper<>();
        qw.in("user_uuid", userUuid);
        return list(qw);
    }

    /**
     * 根据用户uuid 查询用户到角色的关联关系
     * @param userUuid 用户uuid
     * @return 查询用户到角色的关联关系
     */
    default Map<String, List<String>> mapByUserId(Collection<String> userUuid) {
        if (StringUtils.isEmpty(userUuid)) {
            return new HashMap<>(0);
        }
        Map<String, List<RelationUserRole>> map = listByUserId(userUuid).stream().collect(Collectors.groupingBy(RelationUserRole::getUserUuid));

        HashMap<String, List<String>> hashMap = new HashMap<>(map.size());
        map.forEach((k, v) -> hashMap.put(k, v.stream().map(RelationUserRole::getRoleUuid).collect(Collectors.toList())));

        return hashMap;
    }

    /**
     * 向用户合集里填充角色信息
     * @param users 用户
     * @return 角色
     */
    List<SysRole> fillRoles(Collection<SysUser> users);

    /**
     * 查询持有指定角色的用户信息
     * @param roleUuid 角色
     * @return 查询持有指定角色的用户信息
     */
    default List<RelationUserRole> listByRoleUuid(Collection<String> roleUuid) {
        QueryWrapper<RelationUserRole> qw = new QueryWrapper<>();
        qw.in("role_uuid", roleUuid);
        return list(qw);
    }

}