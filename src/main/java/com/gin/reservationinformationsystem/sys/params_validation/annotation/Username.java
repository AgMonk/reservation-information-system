package com.gin.reservationinformationsystem.sys.params_validation.annotation;


import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.PARAMETER;

/**
 * 用户名验证注解
 * @author bx002
 */
@Documented
@Target({PARAMETER, FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = UsernameValidator.class)
public @interface Username {
    String message() default "用户名不合法";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
