package com.gin.reservationinformationsystem.sys.user_info.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gin.reservationinformationsystem.sys.params_validation.annotation.NotEmpty;
import com.gin.reservationinformationsystem.sys.params_validation.annotation.Password;
import com.gin.reservationinformationsystem.sys.params_validation.annotation.Username;
import com.gin.reservationinformationsystem.sys.request.PageParams;
import com.gin.reservationinformationsystem.sys.response.Res;
import com.gin.reservationinformationsystem.sys.shiro.MyUsernamePasswordToken;
import com.gin.reservationinformationsystem.sys.shiro.ShiroUtils;
import com.gin.reservationinformationsystem.sys.user_info.entity.SysUser;
import com.gin.reservationinformationsystem.sys.user_info.requestObject.FilterForSysUser;
import com.gin.reservationinformationsystem.sys.user_info.requestObject.UserForEditInfo;
import com.gin.reservationinformationsystem.sys.user_info.requestObject.UserForManageRole;
import com.gin.reservationinformationsystem.sys.user_info.requestObject.UserForReg;
import com.gin.reservationinformationsystem.sys.user_info.service.RelationRolePermissionService;
import com.gin.reservationinformationsystem.sys.user_info.service.RelationUserRoleService;
import com.gin.reservationinformationsystem.sys.user_info.service.SysUserService;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSort;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.subject.Subject;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

/**
 * 用户管理接口
 * @author bx002
 */
@Slf4j
@RequiredArgsConstructor
@RestController
@Validated
@RequestMapping("/sys/user")
@Api(tags = "用户接口")
@ApiSort(0)
@Transactional(rollbackFor = Exception.class)
public class SysUserController {
    private static final String NAMESPACE = "用户";

    private final SysUserService service;
    private final RelationUserRoleService relationUserRoleService;
    private final RelationRolePermissionService relationRolePermissionService;

    /**
     * 注册新用户
     * @param entity 用户对象
     * @return 结果
     */
    @PostMapping("reg")
    @ApiOperation(value = "注册新用户")
    @ApiOperationSupport(order = 1)
    public Res<Void> reg(@Validated @RequestBody UserForReg entity) {
        final SysUser user = entity.toSysUser();
        final String username = user.getUsername();
        if (username.startsWith("wx_")) {
            return Res.fail(4001, "用户名非法");
        }
        service.reg(user);
        return Res.success("注册成功");
    }

    @PostMapping("login")
    @ApiOperation(value = "登陆")
    @ApiOperationSupport(order = 2)
    public Res<Void> login(@Username @RequestParam String username, @Password @RequestParam String password) {
        Subject subject = SecurityUtils.getSubject();
        //未登录
        if (!subject.isAuthenticated()) {
            subject.login(new MyUsernamePasswordToken(username, password, true));
        }

        return Res.success("登陆成功");
    }


    @PostMapping("logout")
    @ApiOperation(value = "登出")
    @ApiOperationSupport(order = 2)
    public Res<Void> logout() {
        SecurityUtils.getSubject().logout();
        return Res.success("登出成功");
    }

    /**
     * 修改用户的个人信息
     * @param entity 用户对象
     * @return 结果
     */
    @PostMapping("editUserInfo")
    @ApiOperation(value = "修改用户的个人信息")
    @ApiOperationSupport(order = 3)
    public Res<Void> editUserInfo(@RequestBody @Validated UserForEditInfo entity) {
        SysUser user = entity.toSysUser();
        setCurrentUserUuid(user);
        service.editUserInfo(user);
        return Res.success("修改成功");
    }

    /**
     * 用户修改自己的密码
     * @param oldPass 旧密码
     * @param newPass 新密码
     * @return 结果
     */
    @PostMapping("changePass")
    @ApiOperation(value = "用户修改密码")
    @ApiOperationSupport(order = 4)
    public Res<Void> changePass(
            @RequestParam @NotEmpty(value = "oldPass") String oldPass
            , @RequestParam @NotEmpty(value = "newPass") String newPass
    ) {
        service.changePass(service.current().getUuid(), oldPass, newPass);
        SecurityUtils.getSubject().logout();
        return Res.success("密码修改成功，请重新登陆！");
    }

    /**
     * 查询指定用户的详细信息
     * @param uuid 用户uuid
     * @return 用户详情
     */
    @PostMapping("get")
    @ApiOperation(value = "查询一个用户的详情信息", notes = "包括角色信息")
    @ApiOperationSupport(order = 5)
    public Res<SysUser> get(@NotEmpty(value = "uuid") @RequestParam String uuid) {
        SysUser entity = service.getById(uuid);
        // 补充信息
        relationUserRoleService.fillRoles(Collections.singleton(entity));

        return Res.success("查询成功", entity);
    }

    /**
     * 重置用户密码
     * @param uuid 用户uuid
     * @return 新密码
     */
    @PostMapping("resetPass")
    @RequiresPermissions(NAMESPACE + ":重置:用户密码")
    @ApiOperation(value = "重置指定用户的密码", notes = "管理员使用,从data字段返回新密码")
    @ApiOperationSupport(order = 6)
    public Res<String> resetPass(@RequestParam @NotEmpty(value = "uuid") String uuid) {
        String newPass = ShiroUtils.createSalt();
        service.changePass(uuid, newPass);
        return Res.success("重置成功", newPass);
    }

    /**
     * 查询拥有指定角色uuid的用户
     */
    @PostMapping("listByRoleUuid")
    @ApiOperation(value = "查询拥有指定角色的用户", notes = "仅包含uuid和姓名")
    @ApiImplicitParams({@ApiImplicitParam(name = "roleUuid", value = "角色UUID", paramType = "query")})
    @ApiOperationSupport(order = 7)
    public Res<List<SysUser>> listByRoleUuid(@RequestParam @NotEmpty(value = "roleUuid") String roleUuid) {
        QueryWrapper<SysUser> qw = new QueryWrapper<>();
        qw.select("uuid", "name");
        FilterForSysUser filter = new FilterForSysUser();
        filter.setRoleUuid(Collections.singletonList(roleUuid));
        filter.handleQueryWrapper(qw);

        return Res.success("查询成功", service.list(qw));
    }

    /**
     * 分页查询用户信息
     * @param params 分页查询条件
     * @return 用户信息
     */
    @PostMapping("page")
    @RequiresPermissions(NAMESPACE + ":查询:分页")
    @ApiOperation(value = "分页查询用户信息")
    @ApiOperationSupport(order = 8)
    public Res<Page<SysUser>> page(@RequestBody PageParams<FilterForSysUser> params) {
        QueryWrapper<SysUser> qw = new QueryWrapper<>();
//      修改查询条件
        FilterForSysUser filter = params.getFilter();
        if (filter != null) {
            filter.handleQueryWrapper(qw);
        }

        Page<SysUser> page = service.page(new Page<>(params.getPage(), params.getSize()), qw);
//      后续处理
        List<SysUser> records = page.getRecords();

        relationUserRoleService.fillRoles(records);

        return Res.success("查询" + NAMESPACE + "分页数据成功", page);
    }

    /**
     * 管理用户持有的角色
     * @param user 用户
     * @return 结果
     */
    @PostMapping("manageRole")
    @RequiresPermissions(NAMESPACE + ":管理:角色")
    @ApiOperation(value = "管理用户的角色", notes = "将按照roleUuid字段<span style=\"color:red\"><b>覆盖</b></span>指定用户的角色")
    @ApiOperationSupport(order = 9)
    public Res<Void> manageRole(@RequestBody UserForManageRole user) {

        relationUserRoleService.saveRole(user.getUuid(), user.getRoleUuid());

        return Res.success("修改成功");
    }

    /**
     * 查询当前用户的登陆状态
     * @return 当前用户的登陆状态
     */
    @PostMapping("status")
    @ApiOperation(value = "查询当前用户的登陆状态", notes = "已登陆时data字段为当前用户的用户信息，包含角色信息")
    @ApiOperationSupport(order = 10)
    public Res<?> status() {
        if (SecurityUtils.getSubject().isAuthenticated()) {
            SysUser current = service.current();
            relationUserRoleService.fillRoles(Collections.singleton(current));
            relationRolePermissionService.fillPermissions(current.getRoles());
            return Res.success("已登陆", current);
        }
        return Res.fail(3000, "未登录");
    }

    /**
     * 切换指定用户的可用状态
     * @param uuid 用户uuid
     * @return 结果
     */
    @PostMapping("switchAvailable")
    @RequiresPermissions(NAMESPACE + ":修改:用户可用状态")
    @ApiOperation(value = "切换指定用户的可用状态")
    @ApiOperationSupport(order = 11)
    public Res<Void> switchAvailable(@RequestParam @NotEmpty(value = "uuid") String uuid) {
        service.switchAvailable(uuid);
        return Res.success("修改成功");
    }


    private void setCurrentUserUuid(SysUser user) {
        user.setUuid(service.current().getUuid());
    }
}
