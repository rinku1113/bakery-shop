package com.example.shopping.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class HalfWidthNumbersAndHyphenValidator implements ConstraintValidator<HalfWidthNumbersAndHyphen, String> {
    
    @Override
    public void initialize(HalfWidthNumbersAndHyphen constraintAnnotation) {
    }
    
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.isEmpty()) {
            return true; // nullや空文字は別のバリデーションでチェック
        }
        // 全角数字（０-９）や全角ハイフン（－）が含まれていないかチェック
        for (char c : value.toCharArray()) {
            if (c >= '０' && c <= '９') {
                return false; // 全角数字が含まれている
            }
            if (c == '－') {
                return false; // 全角ハイフンが含まれている
            }
        }
        return true;
    }
}

