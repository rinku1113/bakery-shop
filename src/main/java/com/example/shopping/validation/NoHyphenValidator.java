package com.example.shopping.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class NoHyphenValidator implements ConstraintValidator<NoHyphen, String> {
    
    @Override
    public void initialize(NoHyphen constraintAnnotation) {
    }
    
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.isEmpty()) {
            return true; // nullや空文字は別のバリデーションでチェック
        }
        // ハイフン（全角・半角）が含まれていないかチェック
        if (value.contains("-") || value.contains("－")) {
            return false;
        }
        // 全角数字（０-９）が含まれていないかチェック
        for (char c : value.toCharArray()) {
            if (c >= '０' && c <= '９') {
                return false; // 全角数字が含まれている
            }
        }
        return true;
    }
}


