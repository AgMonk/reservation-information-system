package com.gin.reservationinformationsystem.sys.shiro;

import com.gin.reservationinformationsystem.sys.user_info.entity.SysRole;
import com.gin.reservationinformationsystem.sys.user_info.entity.SysUser;
import com.gin.reservationinformationsystem.sys.user_info.service.RelationRolePermissionService;
import com.gin.reservationinformationsystem.sys.user_info.service.RelationUserRoleService;
import com.gin.reservationinformationsystem.sys.user_info.service.SysUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Collections;
import java.util.List;

import static com.gin.reservationinformationsystem.sys.exception.BusinessExceptionEnum.USER_NOT_AVAILABLE;

/**
 * 自定义Realm
 * @author bx002
 */
@Slf4j
@Component(value = "authorizer")
@RequiredArgsConstructor
public class CustomerRealm extends AuthorizingRealm {

    private final SysUserService sysUserService;
    private final RelationUserRoleService relationUserRoleService;
    private final RelationRolePermissionService relationRolePermissionService;
    private final ShiroProperties shiroProperties;
    private static final String NAME = "base";


    @PostConstruct
    public void initRealm() {
        //修改凭证匹配器
        log.info("修改凭证匹配器");
        WechatHashedCredentialsMatcher credentialsMatcher = new WechatHashedCredentialsMatcher();
        //密码加密方式使用md5
        credentialsMatcher.setHashAlgorithmName("md5");
        //md5加密时使用的hash散列次数
        credentialsMatcher.setHashIterations(shiroProperties.getIterations());
        setCredentialsMatcher(credentialsMatcher);

        //开启认证缓存 并设置缓存名称
        log.info("开启认证缓存 并设置缓存名称");
        setAuthenticationCachingEnabled(true);
        setAuthenticationCacheName("authenticationCache_" + NAME);
        //开启授权缓存 并设置缓存名称
        setAuthorizationCachingEnabled(true);
        setAuthorizationCacheName("authorizationCache_" + NAME);
        //设置缓存管理器
        //创建时会把 上方的两个名称传入 可以作为Redis的hash名称
//        setCacheManager(RedisShiroCache::new);
        log.info("开启缓存");
        setCachingEnabled(true);
    }

    /**
     * 权限校验
     * @param principalCollection 权限
     * @return 校验结果
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();

        //获取身份信息
        String username = String.valueOf(principalCollection.getPrimaryPrincipal());
        SysUser user = sysUserService.findByUsername(username);

        List<SysRole> sysRoles = relationUserRoleService.fillRoles(Collections.singleton(user));
        relationRolePermissionService.fillPermissions(sysRoles);

        for (SysRole role : sysRoles) {
            simpleAuthorizationInfo.addRole(role.getName());
            simpleAuthorizationInfo.addStringPermissions(role.getPermissions());
        }


        return simpleAuthorizationInfo;
    }

    private static void addPermission(SimpleAuthorizationInfo simpleAuthorizationInfo, String namespace, String action, String target) {
        simpleAuthorizationInfo.addStringPermission(String.format("%s:%s:%s", namespace, action, target));
    }

    private static void addPermissions(SimpleAuthorizationInfo simpleAuthorizationInfo, String namespace, String target, String... actions) {
        for (String action : actions) {
            addPermission(simpleAuthorizationInfo, namespace, action, target);
        }
    }



    /**
     * 登陆验证
     * @param authenticationToken token
     * @return 登陆结果
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        String username = (String) authenticationToken.getPrincipal();
        SysUser user = sysUserService.findByUsername(username);

        if (user != null) {
            USER_NOT_AVAILABLE.assertTrue(user.getAvailable());

            return new SimpleAuthenticationInfo(
                    user.getUsername()
                    , user.getPassword()
                    , new SimpleByteSource(user.getSalt())
                    , this.getName()
            );
        }

        return null;
    }
}
