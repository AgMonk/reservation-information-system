package com.gin.reservationinformationsystem.module.merchant.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gin.reservationinformationsystem.module.merchant.entity.MerchantTypePo;
import com.gin.reservationinformationsystem.sys.service.PageService;
import com.gin.reservationinformationsystem.sys.service.ValidateService;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author bx002
 */
@Transactional(rollbackFor = Exception.class)
public interface MerchantTypePoService extends IService<MerchantTypePo>, PageService<MerchantTypePo>, ValidateService<MerchantTypePo>,IAvatarService {

    @Override
    default String dirName(){
        return "merchantType";
    };
}