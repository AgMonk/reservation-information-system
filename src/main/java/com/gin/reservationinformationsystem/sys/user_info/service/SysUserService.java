package com.gin.reservationinformationsystem.sys.user_info.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.gin.reservationinformationsystem.sys.shiro.ShiroUtils;
import com.gin.reservationinformationsystem.sys.user_info.entity.SysUser;
import com.gin.reservationinformationsystem.sys.user_info.inter.HasUser;
import com.gin.reservationinformationsystem.sys.utils.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.gin.reservationinformationsystem.sys.exception.BusinessExceptionEnum.*;

/**
 * @author bx002
 */
@Transactional(rollbackFor = Exception.class)
public interface SysUserService extends IService<SysUser> {

    default void validUuid(String uuid){
        USER_NOT_EXISTS.assertNotEmpty(uuid);
        USER_NOT_EXISTS.assertNotEmpty(getById(uuid));
    }

    /**
     * 当前登陆的用户
     * @return 用户
     */
    default SysUser current() {
        return findByUsername((String) SecurityUtils.getSubject().getPrincipal());
    }

    /**
     * 当前用户的uuid
     * @return 当前用户的uuid
     */
    default String getMyUuid() {
        return current().getUuid();
    }

    /**
     * 根据用户名查询用户
     * @param username 用户名
     * @return 用户
     */
    default SysUser findByUsername(String username) {
        QueryWrapper<SysUser> qw = new QueryWrapper<>();
        qw.eq("username", username);
        return getOne(qw);
    }

    /**
     * 对密码执行md5编码
     * @param pass 密码
     * @param salt 盐
     * @return 编码结果
     */
    String doMd5(String pass, String salt);

    /**
     * 注册新用户
     * @param user 新用户
     */
    default void reg(SysUser user) {
        USER_EXISTS.assertNull(findByUsername(user.getUsername()));
        String salt = ShiroUtils.createSalt();
        user.setSalt(salt);
        user.setPassword(doMd5(user.getPassword(), salt));
        user.setTimestampReg(ZonedDateTime.now().toEpochSecond());
        save(user);
    }

    /**
     * 修改用户密码
     * @param userUuid 用户uuid
     * @param password 密码
     */
    default void changePass(String userUuid, String password) {
        SysUser user = assertUserExists(userUuid);
        SysUser u = new SysUser();
        u.setUuid(userUuid);
        u.setPassword(doMd5(password, user.getSalt()));
        updateById(u);
    }

    /**
     * 验证旧密码后 修改用户密码
     * @param userUuid 用户uuid
     * @param oldPass  旧密码
     * @param newPass  新密码
     */
    default void changePass(String userUuid, String oldPass, String newPass) {
        SysUser user = assertUserExists(userUuid);
        String oldMd5 = doMd5(oldPass, user.getSalt());
        USER_OLD_PASS_ERROR.assertEqu(user.getPassword(), oldMd5);

        changePass(userUuid, newPass);
    }

    /**
     * 修改用户的个人信息
     * @param user 用户
     */
    default void editUserInfo(SysUser user) {
        SysUser u = new SysUser();
        u.setUuid(user.getUuid());
        u.setPhone(user.getPhone());
        u.setName(user.getName());
        updateById(u);
    }

    /**
     * 切换用户的可用状态
     * @param userUuid 用户uuid
     */
    default void switchAvailable(String userUuid){
        SysUser user = assertUserExists(userUuid);
        SysUser u = new SysUser();
        u.setUuid(userUuid);
        u.setAvailable(!user.getAvailable());
        updateById(u);
    }

    /**
     * 断言用户uuid不为空 且对应的用户存在
     * @param userUuid 用户uuid
     * @return 用户
     */
    default SysUser assertUserExists(String userUuid){
        UUID_NOT_EMPTY.assertNotNull(userUuid);
        SysUser user = getById(userUuid);
        USER_NOT_EXISTS.assertNotNull(user);
        return user;
    }

    /**
     * 以uuid查询对应的用户姓名映射
     * @param uuid uuid
     * @return 用户姓名map
     */
    default HashMap<String,String> getUserNameMap(Collection<String> uuid){
        QueryWrapper<SysUser> qw = new QueryWrapper<>();
        qw.select("uuid","name").in("uuid",uuid);
        HashMap<String, String> map = new HashMap<>();
        list(qw).forEach(i->map.put(i.getUuid(),i.getName()));
        return map;
    }



    /**
     * 填充一个含有用户字段的对象的地图名称
     * @param hasUser 含有用户字段的对象
     */
    default void filUserName(HasUser hasUser) {
        SysUser user = getById(hasUser.getUserUuid());
        if (user != null) {
            hasUser.setUserName(user.getName());
        }
    }

    /**
     * 查询用户id到名称的映射
     * @param uuid id范围
     * @return 用户id到名称的映射
     */
    default Map<String, SysUser> mapByIds(Collection<String> uuid) {
        if (StringUtils.isEmpty(uuid)) {
            return new HashMap<>();
        }
        return listByIds(uuid).stream().collect(Collectors.toMap(SysUser::getUuid, i -> i));
    }

    /**
     * 填充多个含有用户字段的对象的用户名称
     * @param hasUsers 含有用户字段的对象
     */
    default void fillUserNames(Collection<? extends HasUser> hasUsers) {
        if (StringUtils.isEmpty(hasUsers)) {
            return;
        }
        Map<String, SysUser> map = mapByIds(hasUsers.stream().map(HasUser::getUserUuid).distinct().collect(Collectors.toList()));
        for (HasUser hasUser : hasUsers) {
            hasUser.setUserName(map.get(hasUser.getUserUuid()).getName());
        }
    }

    /**
     * 查询拥有指定名称角色的用户
     * @param roleName 角色名
     */
    List<SysUser> listByRoleName(String roleName);
}