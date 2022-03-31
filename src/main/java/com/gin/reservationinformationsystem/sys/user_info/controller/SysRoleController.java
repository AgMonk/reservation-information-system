package com.gin.reservationinformationsystem.sys.user_info.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gin.reservationinformationsystem.sys.params_validation.annotation.NotEmpty;
import com.gin.reservationinformationsystem.sys.request.PageParams;
import com.gin.reservationinformationsystem.sys.response.Res;
import com.gin.reservationinformationsystem.sys.user_info.entity.SysRole;
import com.gin.reservationinformationsystem.sys.user_info.requestObject.FilterForSysRole;
import com.gin.reservationinformationsystem.sys.user_info.service.RelationRolePermissionService;
import com.gin.reservationinformationsystem.sys.user_info.service.SysRoleService;
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

import static com.gin.reservationinformationsystem.sys.exception.BusinessExceptionEnum.ROLE_EXISTS;

/**
 * 角色管理接口
 * @author bx002
 */
@RestController
@Slf4j
@Validated
@RequiredArgsConstructor
@RequestMapping("/sys/role")
@Api(tags = "角色管理接口")
@ApiSort(1)
@Transactional(rollbackFor = Exception.class)
public class SysRoleController {
    private static final String NAMESPACE = "角色";

    private final SysRoleService service;
    private final RelationRolePermissionService relationRolePermissionService;


    @PostMapping("add")
    @RequiresPermissions(NAMESPACE + ":添加:*")
    @ApiOperation(value = "添加新角色")
    public Res<Void> add(@RequestParam @NotEmpty(value = "name") String name) {
        ROLE_EXISTS.assertNull(service.getByName(name));
        SysRole entity = new SysRole();
        entity.createUuid();
        entity.setName(name);
        service.save(entity);
        return Res.success("添加成功");
    }

    @PostMapping("update")
    @RequiresPermissions(NAMESPACE + ":修改:*")
    @ApiOperation(value = "修改角色", notes = "将按照permissions字段<span style=\"color:red\"><b>覆盖</b></span>指定角色拥有的权限")
    public Res<Void> update(@RequestBody @Validated SysRole entity) {
        service.banAdmin(entity.getUuid());
        service.edit(entity);
        return Res.success("修改成功");
    }

    @PostMapping("del")
    @RequiresPermissions(NAMESPACE + ":删除:*")
    @ApiOperation(value = "删除角色")
    public Res<Void> del(@NotEmpty(value = "uuid") @RequestParam String uuid) {
        if (service.getById(uuid) == null) {
            return Res.fail(4000, "uuid错误");
        }
        service.banAdmin(uuid);
        service.removeById(uuid);
        return Res.success("删除成功");
    }

    @PostMapping("findAll")
    @ApiOperation(value = "查询所有角色")
    public Res<List<SysRole>> findAll() {
        QueryWrapper<SysRole> qw = new QueryWrapper<>();

        List<SysRole> list = service.list(qw);
        relationRolePermissionService.fillPermissions(list);
        return Res.success("查询" + NAMESPACE + "全部数据成功", list);
    }

    @PostMapping("page")
    @RequiresPermissions(NAMESPACE + ":查询:分页")
    @ApiOperation(value = "分页查询角色")
    public Res<Page<SysRole>> page(@RequestBody PageParams<FilterForSysRole> params) {
        QueryWrapper<SysRole> qw = new QueryWrapper<>();
//      修改查询条件
        FilterForSysRole filter = params.getFilter();
        if (filter != null) {
            filter.handleQueryWrapper(qw);
        }

        Page<SysRole> page = service.page(new Page<>(params.getPage(), params.getSize()), qw);
//      后续处理
        List<SysRole> records = page.getRecords();
        relationRolePermissionService.fillPermissions(records);

        return Res.success("查询" + NAMESPACE + "分页数据成功", page);
    }


}
