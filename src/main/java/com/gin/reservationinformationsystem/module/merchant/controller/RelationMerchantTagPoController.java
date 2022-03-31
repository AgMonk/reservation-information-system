package com.gin.reservationinformationsystem.module.merchant.controller;

import com.gin.reservationinformationsystem.module.merchant.bo.merchant_tag.RelationMerchantTagPo4Create;
import com.gin.reservationinformationsystem.module.merchant.service.MerchantPoService;
import com.gin.reservationinformationsystem.module.merchant.service.MerchantTagPoService;
import com.gin.reservationinformationsystem.module.merchant.service.RelationMerchantTagPoService;
import com.gin.reservationinformationsystem.sys.response.Res;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 商户持有标签管理接口
 * @author bx002
 */
@RestController
@Slf4j
@Validated
@RequiredArgsConstructor
@RequestMapping("merchant/tag")
@Api(tags = RelationMerchantTagPoController.NAMESPACE + "相关接口")
@Transactional(rollbackFor = Exception.class)
public class RelationMerchantTagPoController {
    public static final String NAMESPACE = "商户持有标签";

    private final RelationMerchantTagPoService service;
    private final MerchantPoService merchantPoService;
    private final MerchantTagPoService merchantTagPoService;

    @PostMapping("addTagToMerchant")
    @RequiresPermissions(NAMESPACE + ":添加:*")
    @ApiOperation(value = "添加" + NAMESPACE)
    public Res<Void> addTagToMerchant(@RequestBody @Validated RelationMerchantTagPo4Create entity) {

        merchantPoService.assertUuidExits(entity.getMerchantUuid());
        merchantTagPoService.assertUuidExits(entity.getTagUuid());

        service.save(entity.toRelationMerchantTagPo());
        return Res.success("添加成功");
    }

    @PostMapping("delTagFromMerchant/{uuid}")
    @RequiresPermissions(NAMESPACE + ":删除:*")
    @ApiOperation(value = "删除" + NAMESPACE)
    public Res<Void> del(@PathVariable String uuid) {
        service.assertUuidExits(uuid);
        service.removeById(uuid);
        return Res.success("删除成功");
    }

}
