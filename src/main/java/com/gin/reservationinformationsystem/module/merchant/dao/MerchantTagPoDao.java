package com.gin.reservationinformationsystem.module.merchant.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gin.reservationinformationsystem.module.merchant.entity.MerchantTagPo;
import org.apache.ibatis.annotations.CacheNamespace;
import org.apache.ibatis.annotations.CacheNamespaceRef;
import org.springframework.stereotype.Repository;

/**
 * @author bx002
 */
@Repository
@CacheNamespace(flushInterval = 5L * 60 * 1000)
@CacheNamespaceRef(MerchantPoDao.class)
public interface MerchantTagPoDao extends BaseMapper<MerchantTagPo> {
}