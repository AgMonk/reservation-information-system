package com.gin.reservationinformationsystem.module.merchant.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gin.reservationinformationsystem.module.merchant.bo.type.Filter4MerchantTypePo;
import com.gin.reservationinformationsystem.module.merchant.bo.type.MerchantType4Update;
import com.gin.reservationinformationsystem.module.merchant.entity.MerchantTypePo;
import com.gin.reservationinformationsystem.module.merchant.service.MerchantTypePoService;
import com.gin.reservationinformationsystem.sys.request.PageParams;
import com.gin.reservationinformationsystem.sys.response.Res;
import com.gin.reservationinformationsystem.sys.utils.FileUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * 商户类型管理接口
 * @author bx002
 */
@RestController
@Slf4j
@Validated
@RequiredArgsConstructor
@RequestMapping("merchant/type")
@Api(tags = MerchantTypePoController.NAMESPACE + "相关接口")
@Transactional(rollbackFor = Exception.class)
public class MerchantTypePoController {
    public static final String NAMESPACE = "商户类型";

    private final MerchantTypePoService service;

    @PostMapping("add/{name}")
    @RequiresPermissions(NAMESPACE + ":添加:*")
    @ApiOperation(value = "添加" + NAMESPACE)
    public Res<Void> add(@PathVariable String name) {
        final MerchantTypePo entity = new MerchantTypePo(name);
        entity.createUuid();
        service.save(entity);
        return Res.success("添加成功");
    }

    @PostMapping("update")
    @RequiresPermissions(NAMESPACE + ":修改:*")
    @ApiOperation(value = "修改" + NAMESPACE)
    public Res<Void> update(@RequestBody @Validated MerchantType4Update entity) {
        service.assertUuidExits(entity.getUuid());
        service.updateById(entity.toMerchantTypePo());
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

    @PostMapping("uploadAvatar/{uuid}")
    @RequiresPermissions(NAMESPACE + ":修改:*")
    @ApiOperation(value = "上传" + NAMESPACE + "头像")
    public Res<String> uploadAvatar(@PathVariable String uuid, MultipartFile file) throws IOException {
        service.assertUuidExits(uuid);
        final String originalFilename = file.getOriginalFilename();
        assert originalFilename != null;
        final String suffix = originalFilename.substring(originalFilename.lastIndexOf("."));

        StringBuilder path = new StringBuilder();
        path.append("/").append("MerchantType").append("/");
        path.append(uuid).append(suffix);

        FileUtils.saveMultipartFile(file, new File("d:/home"+ path));

        return Res.success("上传成功",path.toString());
    }

    @PostMapping("page")
    @RequiresPermissions(NAMESPACE + ":查询:分页")
    @ApiOperation(value = "查询分页" + NAMESPACE)
    public Res<Page<MerchantTypePo>> page(@RequestBody @Validated PageParams<Filter4MerchantTypePo> param) {

        QueryWrapper<MerchantTypePo> qw = new QueryWrapper<>();
//      应用过滤条件

//        查询
        Page<MerchantTypePo> pageData = service.pageWithParams(param, qw);
//      后续处理
        List<MerchantTypePo> records = pageData.getRecords();

        return Res.success("查询" + NAMESPACE + "分页数据成功", pageData);
    }
}
