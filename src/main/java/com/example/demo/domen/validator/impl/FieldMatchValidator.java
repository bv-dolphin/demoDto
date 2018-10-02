package com.example.demo.domen.validator.impl;


import org.apache.commons.beanutils.BeanUtils;
import com.example.demo.domen.validator.FieldMatch;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.lang.reflect.InvocationTargetException;

public class FieldMatchValidator implements ConstraintValidator<FieldMatch, Object> {
    private String firstFieldName;
    private String secondFieldName;

    @Override
    public void initialize(final FieldMatch constraintAnnotation) {
        firstFieldName = constraintAnnotation.first();
        secondFieldName = constraintAnnotation.second();
    }

    @Override
    public boolean isValid(final Object value, final ConstraintValidatorContext context) {
        try {
            final String firstObj = BeanUtils.getProperty(value, firstFieldName);
            final String secondObj = BeanUtils.getProperty(value, secondFieldName);

            boolean isFieldValueEquals = firstObj == null && secondObj == null || firstObj != null && firstObj.equals(secondObj);

            if (!isFieldValueEquals) {
                context.disableDefaultConstraintViolation();
                context.buildConstraintViolationWithTemplate(context.getDefaultConstraintMessageTemplate())
                        .addNode(secondFieldName)
                        .addConstraintViolation();
                return false;
            }

        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException ex) {
            throw new RuntimeException(ex);
        }
        return true;
    }

}
