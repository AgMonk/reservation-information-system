package com.gin.reservationinformationsystem.module.merchant.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gin.reservationinformationsystem.module.merchant.dao.FeedBackMessagePoDao;
import com.gin.reservationinformationsystem.module.merchant.entity.FeedBackMessagePo;
import com.gin.reservationinformationsystem.module.merchant.service.FeedBackMessagePoService;
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
public class FeedBackMessagePoServiceImpl extends ServiceImpl<FeedBackMessagePoDao, FeedBackMessagePo> implements FeedBackMessagePoService {
}