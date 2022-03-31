package com.gin.reservationinformationsystem.sys.params_validation.annotation;

import com.gin.reservationinformationsystem.sys.utils.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.gin.reservationinformationsystem.sys.params_validation.annotation.ValidatorUtils.validInList;

/**
 * @author bx002
 */
public class EffectiveValuesValidator implements ConstraintValidator<EffectiveValues, String>  {
        private boolean nullable;
        private List<String> list;
        private String prefix;


    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (nullable && StringUtils.isEmpty(value)) {
            return true;
        }
        return validInList(value, list, prefix, context);
    }

    @Override
    public void initialize(EffectiveValues constraintAnnotation) {
        this.list = new ArrayList<>(Arrays.asList(constraintAnnotation.values()));
        this.nullable = constraintAnnotation.nullable();
        this.prefix = constraintAnnotation.prefix();
    }
}
