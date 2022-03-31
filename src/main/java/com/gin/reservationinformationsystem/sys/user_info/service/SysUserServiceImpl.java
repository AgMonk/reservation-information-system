package com.gin.reservationinformationsystem.sys.user_info.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gin.reservationinformationsystem.sys.exception.BusinessException;
import com.gin.reservationinformationsystem.sys.shiro.ShiroProperties;
import com.gin.reservationinformationsystem.sys.shiro.ShiroUtils;
import com.gin.reservationinformationsystem.sys.user_info.dao.SysUserDao;
import com.gin.reservationinformationsystem.sys.user_info.entity.RelationUserRole;
import com.gin.reservationinformationsystem.sys.user_info.entity.SysRole;
import com.gin.reservationinformationsystem.sys.user_info.entity.SysUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author bx002
 */
@Service
@Slf4j
@Transactional(rollbackFor = Exception.class)
@RequiredArgsConstructor
public class SysUserServiceImpl extends ServiceImpl<SysUserDao, SysUser> implements SysUserService {

    private final SysRoleService sysRoleService;
    private final RelationUserRoleService relationUserRoleService;

    private final ShiroProperties shiroProperties;

    @Override
    public String doMd5(String pass, String salt) {
        return ShiroUtils.doMd5(pass, salt, shiroProperties.getIterations());
    }

    @Override
    public List<SysUser> listByRoleName(String roleName) {
        SysRole role = sysRoleService.getByName(roleName);
        if (role == null) {
            throw new BusinessException(4000, "角色不存在:" + roleName);
        }

        List<String> userUuid = relationUserRoleService.listByRoleUuid(Collections.singleton(role.getUuid())).stream().map(RelationUserRole::getUserUuid).distinct().collect(Collectors.toList());
        if (userUuid.size() == 0) {
            throw new BusinessException(4000, "不存在持有该角色的用户:" + roleName);
        }
        QueryWrapper<SysUser> qw = new QueryWrapper<>();
        qw.in("uuid", userUuid);
        qw.select("uuid", "name");
        return list(qw);
    }

}