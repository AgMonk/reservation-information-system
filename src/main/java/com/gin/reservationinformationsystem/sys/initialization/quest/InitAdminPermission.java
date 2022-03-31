package com.gin.reservationinformationsystem.sys.initialization.quest;

import com.gin.reservationinformationsystem.sys.user_info.entity.SysPermission;
import com.gin.reservationinformationsystem.sys.user_info.service.SysPermissionService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import static com.gin.reservationinformationsystem.sys.user_info.entity.SysPermission.ADMINISTRATOR_PERMISSION;
import static com.gin.reservationinformationsystem.sys.user_info.entity.SysPermission.ADMINISTRATOR_PERMISSION_STRING;

/**
 * 初始化权限
 * @author bx002
 */
@Component
@Getter
@RequiredArgsConstructor
@Slf4j
@Order(0)
public class InitAdminPermission implements ApplicationRunner {


    private final SysPermissionService sysPermissionService;

    /**
     * 任务内容
     */
    @Override
    public void run(ApplicationArguments args) throws Exception {
        log.info("权限：检查管理员权限是否存在");

        SysPermission administrator = sysPermissionService.getById(ADMINISTRATOR_PERMISSION_STRING);
        if (administrator == null) {
            log.info("管理员权限已添加");
            sysPermissionService.save(ADMINISTRATOR_PERMISSION);
        }else{
            log.info("管理员权限已存在");
        }
    }
}
