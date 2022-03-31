package com.gin.reservationinformationsystem.sys.params_validation.annotation;

import com.gin.reservationinformationsystem.sys.utils.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

/**
 * @author bx002
 */
public class IsPatternValidator implements ConstraintValidator<IsPattern, String> {
    private boolean nullable;
    private String prefix;
    private String message;


    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (nullable && StringUtils.isEmpty(value)) {
            return true;
        }
        try {
            Pattern.compile(value);
        } catch (Exception e) {
            ValidatorUtils.changeMessage(context, this.prefix + this.message);
            return false;
        }
        return true;
    }

    @Override
    public void initialize(IsPattern constraintAnnotation) {
        this.nullable = constraintAnnotation.nullable();
        this.prefix = constraintAnnotation.prefix();
        this.message = constraintAnnotation.message();
    }
}
