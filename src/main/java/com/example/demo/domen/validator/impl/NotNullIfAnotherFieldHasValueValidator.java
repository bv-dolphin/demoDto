package com.example.demo.domen.validator.impl;

import org.apache.commons.beanutils.BeanUtils;
import com.example.demo.domen.validator.NotNullIfAnotherFieldHasValue;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.lang.reflect.InvocationTargetException;

public class NotNullIfAnotherFieldHasValueValidator implements ConstraintValidator<NotNullIfAnotherFieldHasValue, Object> {
    private String fieldName;
    private String dependFieldName;

    @Override
    public void initialize(NotNullIfAnotherFieldHasValue annotation) {
        fieldName = annotation.fieldName();
        dependFieldName = annotation.dependFieldName();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext ctx) {

        if (value == null) {
            return true;
        }

        try {
            String fieldValue = BeanUtils.getProperty(value, fieldName);
            String dependFieldValue = BeanUtils.getProperty(value, dependFieldName);
            boolean isFieldValue = fieldValue != null && !fieldValue.isEmpty();
            boolean isdependField = dependFieldValue != null && dependFieldValue.isEmpty();

            if (isFieldValue && isdependField) {
                ctx.disableDefaultConstraintViolation();
                ctx.buildConstraintViolationWithTemplate(ctx.getDefaultConstraintMessageTemplate())
                        .addNode(dependFieldName)   //при заполнении возможно использовать ControllerUtils.getFieldErrors, очень удобно;
                        .addConstraintViolation();
                return false;
            }

        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException ex) {
            throw new RuntimeException(ex);
        }

        return true;
    }


}
