package com.gin.reservationinformationsystem.module.merchant.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gin.reservationinformationsystem.module.merchant.bo.tag.MerchantTagBo;
import com.gin.reservationinformationsystem.module.merchant.entity.MerchantPo;
import com.gin.reservationinformationsystem.module.merchant.entity.MerchantTagPo;
import com.gin.reservationinformationsystem.module.merchant.entity.RelationMerchantTagPo;
import com.gin.reservationinformationsystem.sys.service.PageService;
import com.gin.reservationinformationsystem.sys.service.ValidateService;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author bx002
 */
@Transactional(rollbackFor = Exception.class)
public interface RelationMerchantTagPoService extends IService<RelationMerchantTagPo>, PageService<RelationMerchantTagPo>, ValidateService<RelationMerchantTagPo> {

     List<MerchantTagBo> listByMerchantUuid(String merchantUuid);

     Map<String,List<MerchantTagBo>> mapByMerchantUuid(List<String> merchantUuid);

     default void fillTags(Collection<MerchantPo> list){
          final Map<String, List<MerchantTagBo>> map = mapByMerchantUuid(list.stream().map(MerchantPo::getUuid).collect(Collectors.toList()));
          list.forEach(item->item.setTags(map.get(item.getUuid())));
     }

      List<MerchantTagPo> listUsedTags();

}