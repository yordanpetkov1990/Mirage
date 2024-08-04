package nightclub.web.nightclub.validation.annotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import nightclub.web.nightclub.validation.UniqueEmailValidator;
import nightclub.web.nightclub.validation.UniquePhoneNumberValidator;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = UniquePhoneNumberValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface UniquePhoneNumber {
    String message() default "Phone number already exist";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}