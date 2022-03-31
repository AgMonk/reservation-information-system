package com.gin.reservationinformationsystem.sys.user_info.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.gin.reservationinformationsystem.sys.user_info.entity.SysRole;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static com.gin.reservationinformationsystem.sys.exception.BusinessExceptionEnum.FORBIDDEN_UPDATE;
import static com.gin.reservationinformationsystem.sys.user_info.entity.SysRole.ADMINISTRATOR_ROLE;

/**
 * @author bx002
 */
@Transactional(rollbackFor = Exception.class)
public interface SysRoleService extends IService<SysRole> {

    default SysRole getByName(String name) {
        QueryWrapper<SysRole> qw = new QueryWrapper<>();
        qw.eq("name", name);
        return getOne(qw);
    }

    default List<SysRole> listByName(List<String> name) {
        QueryWrapper<SysRole> qw = new QueryWrapper<>();
        qw.in("name", name);
        return list(qw);
    }

    /**
     * 编辑角色
     * @param role 角色
     */
    void edit(SysRole role);

    /**
     * 禁止对超管角色的操作
     * @param roleUuid 角色uuid
     */
    default void banAdmin(String roleUuid) {
        FORBIDDEN_UPDATE.assertTrue(!getById(roleUuid).getName().equals(ADMINISTRATOR_ROLE));
    }

    /**
     * 禁止对超管角色的操作
     * @param roleUuid 角色uuid
     */
    default void banAdmin(List<String> roleUuid) {
        FORBIDDEN_UPDATE.assertTrue(!listByIds(roleUuid).stream().map(SysRole::getName).collect(Collectors.toList()).contains(ADMINISTRATOR_ROLE));
    }


}