package bg.codeacademy.cakeShop.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class IbanValidator implements ConstraintValidator<ValidIban, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        if (value != null) {
            if (!value.trim().isEmpty()) {
                return value.matches("^[A-Z]{2}[0-9]{2}[A-Z0-9]{1,30}$");
            }
        }
        return false;
    }
}
