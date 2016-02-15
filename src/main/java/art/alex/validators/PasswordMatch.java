package art.alex.validators;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy=PasswordMatchImpl.class)

public @interface PasswordMatch {
 String message() default "{art.alex.user.confirmPassword}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    /**
     * First field
     */
    String password();

    /**
     * Second field
     */
    String confirmPassword();

}
