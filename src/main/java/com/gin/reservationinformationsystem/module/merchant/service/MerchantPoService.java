package com.gin.reservationinformationsystem.module.merchant.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gin.reservationinformationsystem.module.merchant.entity.MerchantPo;
import com.gin.reservationinformationsystem.sys.service.PageService;
import com.gin.reservationinformationsystem.sys.service.ValidateService;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author bx002
 */
@Transactional(rollbackFor = Exception.class)
public interface MerchantPoService extends IService<MerchantPo>, PageService<MerchantPo>, ValidateService<MerchantPo>,IAvatarService {

    @Override
    default String dirName(){
        return "merchant";
    };
}