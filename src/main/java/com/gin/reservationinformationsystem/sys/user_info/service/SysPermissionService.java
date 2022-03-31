package com.gin.reservationinformationsystem.sys.user_info.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.IService;
import com.gin.reservationinformationsystem.sys.user_info.entity.SysPermission;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author bx002
 */
@Transactional(rollbackFor = Exception.class)
public interface SysPermissionService extends IService<SysPermission> {
}