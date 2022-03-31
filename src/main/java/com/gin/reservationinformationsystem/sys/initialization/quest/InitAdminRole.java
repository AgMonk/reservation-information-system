package com.gin.reservationinformationsystem.sys.initialization.quest;

import com.gin.reservationinformationsystem.sys.user_info.entity.SysRole;
import com.gin.reservationinformationsystem.sys.user_info.service.RelationRolePermissionService;
import com.gin.reservationinformationsystem.sys.user_info.service.SysRoleService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

import static com.gin.reservationinformationsystem.sys.user_info.entity.SysPermission.ADMINISTRATOR_PERMISSION_STRING;
import static com.gin.reservationinformationsystem.sys.user_info.entity.SysRole.ADMINISTRATOR_ROLE;

/**
 * 初始化任务模板
 * @author bx002
 */
@Component
@Getter
@Slf4j
@RequiredArgsConstructor
@Order(1)
public class InitAdminRole implements ApplicationRunner {

    private final SysRoleService sysRoleService;
    private final RelationRolePermissionService relationRolePermissionService;

    /**
     * 任务内容
     */
    @Override
    public void run(ApplicationArguments args) throws Exception {
        log.info("角色：检查超管角色是否存在");
        
        SysRole administrator = sysRoleService.getByName(ADMINISTRATOR_ROLE);
        if (administrator == null) {
//          角色不存在 新建
            administrator = new SysRole();
            administrator.setName(ADMINISTRATOR_ROLE);
            administrator.createUuid();
            sysRoleService.save(administrator);
            log.info("已添加超级管理员角色");
        } else {
//            角色存在，查询已有权限
            relationRolePermissionService.fillPermissions(Collections.singleton(administrator));
        }
        List<String> permissions = administrator.getPermissions();
        if (permissions == null || !permissions.contains(ADMINISTRATOR_PERMISSION_STRING)) {
            relationRolePermissionService.savePermissions(administrator.getUuid(), Collections.singletonList(ADMINISTRATOR_PERMISSION_STRING));
            log.info("已添加超管权限");
        } else {
            log.info("权限已存在");
        }
    }
}
