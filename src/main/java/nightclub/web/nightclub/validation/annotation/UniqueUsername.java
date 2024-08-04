package nightclub.web.nightclub.validation.annotation;


import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import nightclub.web.nightclub.validation.UniqueUsernameValidator;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = UniqueUsernameValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface UniqueUsername {
    String message() default "Username already exist";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}