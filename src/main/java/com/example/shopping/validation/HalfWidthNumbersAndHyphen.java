package com.example.shopping.validation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Documented
@Constraint(validatedBy = HalfWidthNumbersAndHyphenValidator.class)
@Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface HalfWidthNumbersAndHyphen {
    String message() default "住所の数字とハイフンは半角で入力してください";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}

