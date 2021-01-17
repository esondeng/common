package com.eson.common.core.utils;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.apache.commons.collections4.CollectionUtils;
import org.hibernate.validator.HibernateValidator;


/**
 * @author dengxiaolin
 * @since 2021/01/17
 */
public class ValidatorUtils {
    private static ValidatorFactory factory = Validation.byProvider(HibernateValidator.class)
            .configure()
            .buildValidatorFactory();

    public static Validator validator = factory.getValidator();

    public static <T> void validate(T target, Class<?>... groups) {
        Set<ConstraintViolation<T>> violationSet = validator.validate(target, groups);
        if (CollectionUtils.isEmpty(violationSet)) {
            return;
        }

        StringBuilder message = new StringBuilder();
        violationSet.forEach(violation -> message.append(violation.getMessage()).append(" ,"));
        Assert.throwIfTrue(message.length() > 0, message.substring(0, message.length() - 1));
    }
}
