package uz.anotation.role_constraint;



import uz.model.enums.Role;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;

public class RoleValidator implements ConstraintValidator<uz.anotation.role_constraint.Role, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return Arrays.stream(Role.values()).anyMatch(u -> u.name().equals(value));

    }
}
