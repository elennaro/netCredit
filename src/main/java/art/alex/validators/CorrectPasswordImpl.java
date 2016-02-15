package art.alex.validators;

import art.alex.services.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

class CorrectPasswordImpl implements ConstraintValidator<CorrectPassword, String> {


    @Autowired
    CustomUserDetailsService customUserDetailsService;

    @Override
    public void initialize(CorrectPassword cp) { }

    @Override
    public boolean isValid(String password, ConstraintValidatorContext ctx) {
        return password != null && customUserDetailsService.checkPassword(password);
    }

}
