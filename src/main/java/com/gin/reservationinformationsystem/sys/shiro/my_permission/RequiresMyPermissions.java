package com.gin.reservationinformationsystem.sys.shiro.my_permission;

import java.lang.annotation.*;

/**
 * @author bx002
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RequiresMyPermissions {

    String namespace();

    String action();

    /**
     * 前置校验资源权限表达式
     * @return 资源的权限字符串表示(如 “ 字节跳动 ” 下的 “ 抖音 ” 可以表达为BYTE_DANCE : TIK_TOK)
     */
    String pre() default "";

    /**
     * 后置校验资源权限表达式
     */
    String post() default "";


}
