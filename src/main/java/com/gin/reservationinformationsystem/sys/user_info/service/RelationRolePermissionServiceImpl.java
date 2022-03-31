package com.gin.reservationinformationsystem.sys.user_info.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gin.reservationinformationsystem.sys.user_info.dao.RelationRolePermissionDao;
import com.gin.reservationinformationsystem.sys.user_info.entity.RelationRolePermission;
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
public class RelationRolePermissionServiceImpl extends ServiceImpl<RelationRolePermissionDao, RelationRolePermission> implements RelationRolePermissionService {
}