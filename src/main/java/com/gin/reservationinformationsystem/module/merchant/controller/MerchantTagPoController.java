package com.gin.reservationinformationsystem.module.merchant.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gin.reservationinformationsystem.module.merchant.bo.tag.Filter4MerchantTagPo;
import com.gin.reservationinformationsystem.module.merchant.entity.MerchantTagPo;
import com.gin.reservationinformationsystem.module.merchant.service.MerchantTagPoService;
import com.gin.reservationinformationsystem.sys.request.PageParams;
import com.gin.reservationinformationsystem.sys.response.Res;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 商户标签管理接口
 * @author bx002
 */
@RestController
@Slf4j
@Validated
@RequiredArgsConstructor
@RequestMapping("merchant/tag")
@Api(tags = MerchantTagPoController.NAMESPACE + "相关接口")
@Transactional(rollbackFor = Exception.class)
public class MerchantTagPoController {
    public static final String NAMESPACE = "商户标签";

    private final MerchantTagPoService service;

    @PostMapping("add")
    @RequiresPermissions(NAMESPACE + ":添加:*")
    @ApiOperation(value = "添加" + NAMESPACE)
    public Res<Void> add(@RequestParam String name) {
        final MerchantTagPo entity = new MerchantTagPo();
        entity.setName(name);
        entity.createUuid();
        service.save(entity);
        return Res.success("添加成功");
    }

    @PostMapping("update")
    @RequiresPermissions(NAMESPACE + ":修改:*")
    @ApiOperation(value = "修改" + NAMESPACE)
    public Res<Void> update(@RequestBody @Validated MerchantTagPo entity) {
        service.assertUuidExits(entity.getUuid());
        service.updateById(entity);
        return Res.success("修改成功");
    }

    @PostMapping("del/{uuid}")
    @RequiresPermissions(NAMESPACE + ":删除:*")
    @ApiOperation(value = "删除" + NAMESPACE)
    public Res<Void> del(@PathVariable String uuid) {
        service.assertUuidExits(uuid);
        service.removeById(uuid);
        return Res.success("删除成功");
    }

    @PostMapping("page")
    @RequiresPermissions(NAMESPACE + ":查询:分页")
    @ApiOperation(value = "查询分页" + NAMESPACE)
    public Res<Page<MerchantTagPo>> page(@RequestBody @Validated PageParams<Filter4MerchantTagPo> param) {

        QueryWrapper<MerchantTagPo> qw = new QueryWrapper<>();
//      应用过滤条件

//        查询
        Page<MerchantTagPo> pageData = service.pageWithParams(param, qw);
//      后续处理
        List<MerchantTagPo> records = pageData.getRecords();

        return Res.success("查询" + NAMESPACE + "分页数据成功", pageData);
    }
}
