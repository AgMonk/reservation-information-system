package com.gin.reservationinformationsystem.sys.user_info.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.gin.reservationinformationsystem.sys.params_validation.annotation.NotEmpty;
import com.gin.reservationinformationsystem.sys.params_validation.group.Create;
import com.gin.reservationinformationsystem.sys.response.Res;
import com.gin.reservationinformationsystem.sys.user_info.entity.SysPermission;
import com.gin.reservationinformationsystem.sys.user_info.service.RelationRolePermissionService;
import com.gin.reservationinformationsystem.sys.user_info.service.SysPermissionService;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSort;
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
 * 权限管理接口
 * @author bx002
 */
@RestController
@Slf4j
@Validated
@RequestMapping("/sys/permission")
@ApiSort(2)
@Api(tags = "权限管理接口")
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class)
public class SysPermissionController {
    private static final String NAMESPACE = "权限";

    private final SysPermissionService service;
    private final RelationRolePermissionService relationRolePermissionService;

    @PostMapping("add")
    @RequiresPermissions(NAMESPACE + ":添加:*")
    @ApiOperation(value = "添加权限")
    @ApiOperationSupport(order = 1)
    public Res<Void> add(@RequestBody @Validated(Create.class) SysPermission entity) {
        service.save(entity);
        return Res.success("添加成功");
    }

//    @PostMapping("update")
//    @RequiresPermissions(NAMESPACE + ":修改:*")
//    public Res<?> update(@RequestBody @Validated(Update.class) SysPermission entity) {
//        service.updateById(entity);
//        return Res.success("修改成功");
//    }

    @PostMapping("del")
    @RequiresPermissions(NAMESPACE + ":删除:*")
    @ApiOperation(value = "删除权限")
    @ApiOperationSupport(order = 2)
    public Res<Void> del(@RequestParam @NotEmpty(value = "uuid") String uuid) {
        if (service.getById(uuid) == null) {
            return Res.fail(4000, "uuid错误");
        }
        service.removeById(uuid);
        relationRolePermissionService.removeByPermissionUuid(uuid);
        return Res.success("删除成功");
    }

    @PostMapping("findAll")
    @RequiresPermissions(NAMESPACE + ":查询:全部")
    @ApiOperation(value = "查询全部权限")
    @ApiOperationSupport(order = 3)
    public Res<List<SysPermission>> findAll() {
        QueryWrapper<SysPermission> qw = new QueryWrapper<>();

        return Res.success("查询" + NAMESPACE + "全部数据成功", service.list(qw));
    }

//    @PostMapping("get")
//    public Res<?> get(@NotEmpty(prefix = "uuid") String uuid) {
//        SysPermission entity = service.getById(uuid);
//        // 补充信息
//
//        return Res.success("查询成功", entity);
//    }

//    @PostMapping("page")
//    @RequiresPermissions(NAMESPACE + ":查询:分页")
//    @ApiOperation(value = "分页查询权限")
//    @ApiOperationSupport(order = 4)
//    public Res<?> page(@RequestBody PageParams<SysPermission> params) {
//        QueryWrapper<SysPermission> qw = new QueryWrapper<>();
////      修改查询条件
//        SysPermission filter = params.getFilter();
//        if (filter != null) {
//            filter.handleQueryWrapper(qw);
//        }
//
//        Page<SysPermission> page = service.page(new Page<>(params.getPage(), params.getSize()), qw);
////      后续处理
//        List<SysPermission> records = page.getRecords();
//
//        return Res.success("查询" + NAMESPACE + "分页数据成功", page);
//    }
}
