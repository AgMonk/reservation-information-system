package com.gin.reservationinformationsystem.sys.user_info.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gin.reservationinformationsystem.sys.user_info.dao.SysRoleDao;
import com.gin.reservationinformationsystem.sys.user_info.entity.SysRole;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author bx002
 */
@Service
@Slf4j
@Transactional(rollbackFor = Exception.class)
@RequiredArgsConstructor
public class SysRoleServiceImpl extends ServiceImpl<SysRoleDao, SysRole> implements SysRoleService {

    private final RelationRolePermissionService relationRolePermissionService;

    @Override
    public void edit(SysRole role) {
        updateById(role);
        relationRolePermissionService.savePermissions(role.getUuid(),role.getPermissions());

    }
}