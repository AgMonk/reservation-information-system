package com.gin.reservationinformationsystem.module.merchant.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gin.reservationinformationsystem.module.merchant.dao.MerchantTypePoDao;
import com.gin.reservationinformationsystem.module.merchant.entity.MerchantTypePo;
import com.gin.reservationinformationsystem.module.merchant.service.MerchantTypePoService;
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
public class MerchantTypePoServiceImpl extends ServiceImpl<MerchantTypePoDao, MerchantTypePo> implements MerchantTypePoService {
}