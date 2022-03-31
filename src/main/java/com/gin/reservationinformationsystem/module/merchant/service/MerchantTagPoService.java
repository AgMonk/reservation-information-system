package com.gin.reservationinformationsystem.module.merchant.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gin.reservationinformationsystem.module.merchant.entity.MerchantTagPo;
import com.gin.reservationinformationsystem.sys.service.PageService;
import com.gin.reservationinformationsystem.sys.service.ValidateService;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author bx002
 */
@Transactional(rollbackFor = Exception.class)
public interface MerchantTagPoService extends IService<MerchantTagPo>, PageService<MerchantTagPo>, ValidateService<MerchantTagPo> {
    default Map<String,String> mapById(Collection<String> uuid){
        return listByIds(uuid).stream().collect(Collectors.toMap(MerchantTagPo::getUuid, MerchantTagPo::getName));
    }
}