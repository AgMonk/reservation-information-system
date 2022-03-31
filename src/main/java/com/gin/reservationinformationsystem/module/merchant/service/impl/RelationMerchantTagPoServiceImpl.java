package com.gin.reservationinformationsystem.module.merchant.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gin.reservationinformationsystem.module.merchant.bo.tag.MerchantTagBo;
import com.gin.reservationinformationsystem.module.merchant.dao.RelationMerchantTagPoDao;
import com.gin.reservationinformationsystem.module.merchant.entity.MerchantTagPo;
import com.gin.reservationinformationsystem.module.merchant.entity.RelationMerchantTagPo;
import com.gin.reservationinformationsystem.module.merchant.service.MerchantTagPoService;
import com.gin.reservationinformationsystem.module.merchant.service.RelationMerchantTagPoService;
import com.gin.reservationinformationsystem.sys.utils.StringUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author bx002
 */
@Service
@Slf4j
@Transactional(rollbackFor = Exception.class)
@RequiredArgsConstructor
public class RelationMerchantTagPoServiceImpl extends ServiceImpl<RelationMerchantTagPoDao, RelationMerchantTagPo> implements RelationMerchantTagPoService {
    private final MerchantTagPoService merchantTagPoService;

    @Override
    public List<MerchantTagBo> listByMerchantUuid(String merchantUuid) {
        final QueryWrapper<RelationMerchantTagPo> qw = new QueryWrapper<>();
        qw.eq("merchant_uuid",merchantUuid);
        final List<RelationMerchantTagPo> list = list(qw);

        final List<String> tagUuid = list.stream().map(RelationMerchantTagPo::getTagUuid).distinct().collect(Collectors.toList());
        final Map<String, String> tagMap = merchantTagPoService.mapById(tagUuid);

        return list.stream().map(i->{
            final MerchantTagBo bo = new MerchantTagBo();
            bo.setUuid(i.getUuid());
            bo.setName(tagMap.get(i.getTagUuid()));
            return bo;
        }).collect(Collectors.toList());
    }

    @Override
    public Map<String, List<MerchantTagBo>> mapByMerchantUuid(List<String> merchantUuid) {
        final HashMap<String, List<MerchantTagBo>> map = new HashMap<>();
        if(StringUtils.isEmpty(merchantUuid)){
            return map;
        }
        final QueryWrapper<RelationMerchantTagPo> qw = new QueryWrapper<>();
        qw.in("merchant_uuid",merchantUuid);
        final List<RelationMerchantTagPo> list = list(qw);
        if (list.size()==0){
            return map;
        }
        final List<String> tagUuid = list.stream().map(RelationMerchantTagPo::getTagUuid).distinct().collect(Collectors.toList());
        final Map<String, String> tagMap = merchantTagPoService.mapById(tagUuid);

        list.forEach(item->{
            final String uuid = item.getMerchantUuid();
            final List<MerchantTagBo> data = map.getOrDefault(uuid, new ArrayList<>());
            map.put(uuid,data);
            data.add(new MerchantTagBo(item.getUuid(),tagMap.get(item.getTagUuid())));
        });
        return map;
    }

    @Override
    public List<MerchantTagPo> listUsedTags() {
        final QueryWrapper<RelationMerchantTagPo> qw = new QueryWrapper<>();
        qw.select("tag_uuid").groupBy("tag_uuid");
        final List<String> tagUuid = list(qw).stream().map(RelationMerchantTagPo::getTagUuid).collect(Collectors.toList());
        if (tagUuid.size()==0) {
            return new ArrayList<>();
        }
        return merchantTagPoService.listByIds(tagUuid);
    }
}