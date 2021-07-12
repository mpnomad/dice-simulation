package com.avaloq.api.validation;


import com.avaloq.core.dto.DiceRollDto;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

public class DiceValidation implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return DiceRollDto.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        ValidationUtils.rejectIfEmpty(errors, "diceCount", "diceCount.empty");
        ValidationUtils.rejectIfEmpty(errors, "diceCount", "diceCount.empty");

        DiceRollDto diceRollDto = (DiceRollDto) target;
//        if (diceRollDto.() < 0) {
//            e.rejectValue("age", "negativevalue");
//        } else if (p.getAge() > 110) {
//            e.rejectValue("age", "too.darn.old");
//        }
    }
}
