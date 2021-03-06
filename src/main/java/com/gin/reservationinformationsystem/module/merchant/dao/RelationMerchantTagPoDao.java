package com.gin.reservationinformationsystem.module.merchant.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gin.reservationinformationsystem.module.merchant.entity.RelationMerchantTagPo;
import org.apache.ibatis.annotations.CacheNamespace;
import org.springframework.stereotype.Repository;

/**
 * @author bx002
 */
@Repository
@CacheNamespace(flushInterval = 5L * 60 * 1000)
public interface RelationMerchantTagPoDao extends BaseMapper<RelationMerchantTagPo> {
}