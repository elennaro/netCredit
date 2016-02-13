package art.alex.validators;


import art.alex.entities.User;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
//I know I do not have messages
public class UserValidator implements Validator {

    @Override public boolean supports(Class<?> clazz) {
        return User.class.isAssignableFrom(clazz);
    }

    @Override public void validate(Object target, Errors errors) {
        User user = (User) target;

        String password = user.getPassword();
        String confirmPassword = user.getConfirmPassword();

        if (confirmPassword != null && !password.equals(confirmPassword)) {
            errors.rejectValue("confirmPassword", "user.confirmPassword", "Passwords do not match!");
        }

        if(user.getAge() < 18)
            errors.rejectValue("birthday", "user.birthday", "Bye bye, kiddo, you should be 18!");
    }
}
