package nightclub.web.nightclub.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import nightclub.web.nightclub.services.impl.UserServiceImpl;
import nightclub.web.nightclub.validation.annotation.UniquePhoneNumber;

public class UniquePhoneNumberValidator implements ConstraintValidator<UniquePhoneNumber, String> {

    private final UserServiceImpl userService;


    public UniquePhoneNumberValidator(UserServiceImpl userService) {
        this.userService = userService;
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return (! this.userService.findByPhoneNumber(value));
    }
}