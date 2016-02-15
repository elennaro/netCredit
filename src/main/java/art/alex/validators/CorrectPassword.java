package art.alex.validators;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target( { ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = CorrectPasswordImpl.class)

public @interface CorrectPassword {
    String message() default "{art.alex.user.oldPassword}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
