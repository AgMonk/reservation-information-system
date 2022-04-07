package com.gin.reservationinformationsystem.sys.shiro;

import com.gin.reservationinformationsystem.sys.shiro.my_permission.PermitAdvisor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;

/**
 * @author bx002
 */
@Configuration
public class ShiroConfig {
    /**
     * 开启Shiro的注解(如@RequiresRoles,@RequiresPermissions),需借助SpringAOP扫描使用Shiro注解的类,并在必要时进行安全逻辑验证
     * 配置以下两个bean(DefaultAdvisorAutoProxyCreator和AuthorizationAttributeSourceAdvisor)即可实现此功能
     * @return DefaultAdvisorAutoProxyCreator
     */
    @Bean
    public DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
        advisorAutoProxyCreator.setProxyTargetClass(true);
        return advisorAutoProxyCreator;
    }

    /**
     * 创建安全管理器
     * @param customerRealm CustomerRealm
     * @return DefaultWebSecurityManager
     */
    @Bean
    public DefaultWebSecurityManager defaultWebSecurityManager(CustomerRealm customerRealm) {
        DefaultWebSecurityManager defaultWebSecurityManager = new DefaultWebSecurityManager();
        //自定义Realm
        defaultWebSecurityManager.setRealm(customerRealm);
        //自定义session管理器
        defaultWebSecurityManager.setSessionManager(new ShiroSessionManager());
        return defaultWebSecurityManager;
    }

    @Bean
    public PermitAdvisor permitAdvisor() {
        return new PermitAdvisor();
    }

    /**
     * 开启aop注解支持
     * @param securityManager DefaultWebSecurityManager
     * @return AuthorizationAttributeSourceAdvisor
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(DefaultWebSecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }

    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(DefaultWebSecurityManager defaultWebSecurityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(defaultWebSecurityManager);
        LinkedHashMap<String, String> map = new LinkedHashMap<>();
        //不需要验证的资源

        //swagger2免拦截
        map.put("/swagger-ui/**", "anon");
        map.put("/v2/api-docs", "anon");
        map.put("/v3/api-docs", "anon");
        map.put("/swagger-resources/**", "anon");
        map.put("/webjars/**", "anon");

        map.put("/doc/**", "anon");
        map.put("/doc.html", "anon");

        map.put("/sys/user/status", "anon");
        map.put("/sys/user/reg**", "anon");
        map.put("/sys/user/login**", "anon");
        map.put("/sys/user/wxLogin", "anon");
        map.put("/wx/**", "anon");
        map.put("/article/mainPage", "anon");
        map.put("/test/**", "anon");
        map.put("/merchant/feedback/add", "anon");
        map.put("/merchant/merchant/page", "anon");
        map.put("/merchant/merchant/get/**", "anon");
        map.put("/merchant/merchant/options", "anon");


//        部门
//        map.put("/Department/Member/page/**","d-member");

        //需要验证的资源
//        map.put("/**", "anon");
        map.put("/**", "authc");

        shiroFilterFactoryBean.getFilters().put("authc", new ShiroLoginFilter());
//        shiroFilterFactoryBean.setLoginUrl("/login");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(map);
        return shiroFilterFactoryBean;
    }
}
