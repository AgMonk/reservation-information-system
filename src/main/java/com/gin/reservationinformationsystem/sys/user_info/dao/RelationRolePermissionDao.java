package com.gin.reservationinformationsystem.sys.user_info.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gin.reservationinformationsystem.sys.user_info.entity.RelationRolePermission;
import org.apache.ibatis.annotations.CacheNamespace;
import org.springframework.stereotype.Repository;

/**
 * @author bx002
 */
@Repository
@CacheNamespace(flushInterval = 5L * 60 * 1000)
public interface RelationRolePermissionDao extends BaseMapper<RelationRolePermission> {
}