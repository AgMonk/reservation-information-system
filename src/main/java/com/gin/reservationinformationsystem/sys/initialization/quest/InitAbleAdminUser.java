package com.gin.reservationinformationsystem.sys.initialization.quest;

import com.gin.reservationinformationsystem.sys.user_info.entity.SysRole;
import com.gin.reservationinformationsystem.sys.user_info.entity.SysUser;
import com.gin.reservationinformationsystem.sys.user_info.service.RelationUserRoleService;
import com.gin.reservationinformationsystem.sys.user_info.service.SysRoleService;
import com.gin.reservationinformationsystem.sys.user_info.service.SysUserService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Collections;

import static com.gin.reservationinformationsystem.sys.user_info.entity.SysRole.ADMINISTRATOR_ROLE;
import static com.gin.reservationinformationsystem.sys.user_info.entity.SysUser.ADMINISTRATOR_USER;

/**
 * 初始化任务模板
 * @author bx002
 */
@Component
@Getter
@Slf4j
@RequiredArgsConstructor
@Order(2)
public class InitAbleAdminUser implements ApplicationRunner {

    private final SysUserService sysUserService;
    private final SysRoleService sysRoleService;
    private final RelationUserRoleService relationUserRoleService;
    /**
     * 任务内容
     */
    @Override
    public void run(ApplicationArguments args) throws Exception {
        log.info("用户:检查超级管理员账号是否存在");

        SysUser admin = sysUserService.findByUsername(ADMINISTRATOR_USER);
        if (admin==null){
            admin = new SysUser();
            admin.setUsername(ADMINISTRATOR_USER);
            admin.setName(ADMINISTRATOR_ROLE);
            admin.setPassword("123456");
            admin.setPhone("13977237106");
            admin.createUuid();
            sysUserService.reg(admin);
        }else{
//            用户已存在 查询角色
            relationUserRoleService.fillRoles(Collections.singleton(admin));
        }
        if (!admin.hasRoleByName(ADMINISTRATOR_ROLE)) {
//            没有角色 添加
            SysRole adminRole = sysRoleService.getByName(ADMINISTRATOR_ROLE);
            relationUserRoleService.saveByUserId(admin.getUuid(), Collections.singletonList(adminRole.getUuid()));
            log.info("已为超管用户添加角色");
        }else{
            log.info("已有超管角色");
        }
    }
}
