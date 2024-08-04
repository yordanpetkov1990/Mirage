package nightclub.web.nightclub.validation;


import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import nightclub.web.nightclub.services.impl.UserServiceImpl;
import nightclub.web.nightclub.validation.annotation.UniqueEmail;


public class UniqueEmailValidator implements ConstraintValidator<UniqueEmail, String> {

    private final UserServiceImpl userService;

    public UniqueEmailValidator(UserServiceImpl userService) {
        this.userService = userService;
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return (! this.userService.findUserByEmail(value));
    }
}