package com.gin.reservationinformationsystem.module.merchant.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gin.reservationinformationsystem.module.merchant.bo.merchant.Filter4MerchantPo;
import com.gin.reservationinformationsystem.module.merchant.bo.merchant.MerchantPo4Create;
import com.gin.reservationinformationsystem.module.merchant.bo.merchant.MerchantPo4Update;
import com.gin.reservationinformationsystem.module.merchant.bo.merchant.MerchantPoOptions;
import com.gin.reservationinformationsystem.module.merchant.entity.MerchantPo;
import com.gin.reservationinformationsystem.module.merchant.entity.MerchantTypePo;
import com.gin.reservationinformationsystem.module.merchant.service.MerchantPoService;
import com.gin.reservationinformationsystem.module.merchant.service.MerchantTypePoService;
import com.gin.reservationinformationsystem.sys.bo.FilterOption;
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
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 管理接口
 * @author bx002
 */
@RestController
@Slf4j
@Validated
@RequiredArgsConstructor
@RequestMapping("MerchantPo")
@Api(tags = MerchantPoController.NAMESPACE + "相关接口")
@Transactional(rollbackFor = Exception.class)
public class MerchantPoController {
    public static final String NAMESPACE = "商户";

    private final MerchantPoService service;
    private final MerchantTypePoService merchantTypePoService;

    @PostMapping("add")
    @RequiresPermissions(NAMESPACE + ":添加:*")
    @ApiOperation(value = "添加" + NAMESPACE)
    public Res<Void> add(@RequestBody @Validated MerchantPo4Create entity) {
        service.save(entity.toMerchantPo());
        return Res.success("添加成功");
    }

    @PostMapping("update")
    @RequiresPermissions(NAMESPACE + ":修改:*")
    @ApiOperation(value = "修改" + NAMESPACE)
    public Res<Void> update(@RequestBody @Validated MerchantPo4Update entity) {
        service.assertUuidExits(entity.getUuid());
        service.updateById(entity.toMerchantPo());
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


    @PostMapping("get/{uuid}")
    @ApiOperation(value = "查询单个" + NAMESPACE + "详情")
    public Res<MerchantPo> get(@PathVariable String uuid) {
        MerchantPo entity = service.getById(uuid);
        // todo 补充信息

        return Res.success("查询成功", entity);
    }

    @PostMapping("page")
    @RequiresPermissions(NAMESPACE + ":查询:分页")
    @ApiOperation(value = "查询分页" + NAMESPACE)
    public Res<Page<MerchantPo>> page(@RequestBody @Validated PageParams<Filter4MerchantPo> param) {

        QueryWrapper<MerchantPo> qw = new QueryWrapper<>();
//      应用过滤条件
        qw.orderByDesc("timestamp_created");
//        查询
        Page<MerchantPo> pageData = service.pageWithParams(param, qw);
//      后续处理
        List<MerchantPo> records = pageData.getRecords();

        return Res.success("查询" + NAMESPACE + "分页数据成功", pageData);
    }


    @PostMapping("options")
    @RequiresPermissions(NAMESPACE + ":查询:分页")
    @ApiOperation(value = "查询过滤选项" + NAMESPACE)
    public Res<MerchantPoOptions> options() {
        final MerchantPoOptions options = new MerchantPoOptions();

        final QueryWrapper<MerchantPo> qw = new QueryWrapper<>();
        qw.select("area").groupBy("area");
        options.setArea(service.list(qw).stream().map(MerchantPo::getArea).collect(Collectors.toList()));
        final List<MerchantTypePo> types = merchantTypePoService.list();
        options.setTypes(FilterOption.build(types, type -> new FilterOption(type.getName(), type.getUuid())));

        return Res.success("查询" + NAMESPACE + "过滤选项成功", options);
    }

    @PostMapping("uploadAvatar/{uuid}")
    @RequiresPermissions(NAMESPACE + ":修改:*")
    @ApiOperation(value = "上传" + NAMESPACE + "头像")
    public Res<String> uploadAvatar(@PathVariable String uuid, MultipartFile file) throws IOException {
        service.assertUuidExits(uuid);
        final String path = service.saveAvatar(uuid, file);

        final MerchantPo entity = new MerchantPo();
        entity.setUuid(uuid);
        entity.setAvatar(path);
        service.updateById(entity);

        return Res.success("上传成功", path);
    }
}
