package com.eson.common.core.util;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.HibernateValidator;


/**
 * @author dengxiaolin
 * @since 2021/01/17
 */
public class ValidatorUtils {
    private static ValidatorFactory factory = Validation.byProvider(HibernateValidator.class)
            .configure()
            .buildValidatorFactory();

    private static Validator validator = factory.getValidator();

    public static <T> void validate(T target, Class<?>... groups) {
        String validateResult = getValidateResult(target, groups);
        Assert.throwIfTrue(StringUtils.isNotBlank(validateResult), validateResult);
    }


    public static <T> String getValidateResult(T target, Class<?>... groups) {
        Set<ConstraintViolation<T>> violationSet = validator.validate(target, groups);
        if (CollectionUtils.isEmpty(violationSet)) {
            return "";
        }

        StringBuilder message = new StringBuilder();
        violationSet.forEach(violation -> message.append(violation.getMessage()).append(" ,"));

        return message.substring(0, message.length() - 1);
    }
}
