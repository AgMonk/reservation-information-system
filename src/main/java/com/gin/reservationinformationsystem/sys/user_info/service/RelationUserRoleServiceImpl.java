package com.gin.reservationinformationsystem.sys.user_info.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gin.reservationinformationsystem.sys.user_info.dao.RelationUserRoleDao;
import com.gin.reservationinformationsystem.sys.user_info.entity.RelationUserRole;
import com.gin.reservationinformationsystem.sys.user_info.entity.SysRole;
import com.gin.reservationinformationsystem.sys.user_info.entity.SysUser;
import com.gin.reservationinformationsystem.sys.utils.StringUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author bx002
 */
@Service
@Slf4j
@Transactional(rollbackFor = Exception.class)
@RequiredArgsConstructor
public class RelationUserRoleServiceImpl extends ServiceImpl<RelationUserRoleDao, RelationUserRole> implements RelationUserRoleService {

    private final SysRoleService sysRoleService;
    private final RelationRolePermissionService relationRolePermissionService;

    @Override
    public List<SysRole> fillRoles(Collection<SysUser> users) {

        if (StringUtils.isEmpty(users)) {
            return new ArrayList<>();
        }
        List<String> userUuid = users.stream().map(SysUser::getUuid).distinct().collect(Collectors.toList());
//        用户id 到 角色列表的映射
        Map<String, List<String>> mapByUserId = mapByUserId(userUuid);

        List<String> roleUuid = mapByUserId.values().stream().flatMap(Collection::stream).distinct().collect(Collectors.toList());
        if (StringUtils.isEmpty(roleUuid)) {
            return new ArrayList<>();
        }
        List<SysRole> roles = sysRoleService.listByIds(roleUuid);
        for (SysUser user : users) {
            List<String> roleId = mapByUserId.get(user.getUuid());
            if (StringUtils.isEmpty(roleId)) {
                continue;
            }
            user.setRoles(roles.stream().filter(r -> roleId.contains(r.getUuid())).collect(Collectors.toList()));
        }

        return roles;
    }
}