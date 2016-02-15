package art.alex.validators;

import org.apache.commons.beanutils.BeanUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

class PasswordMatchImpl implements ConstraintValidator<PasswordMatch, Object> {

    private String password;
    private String confirmPassword;

    @Override
    public void initialize(PasswordMatch pm) {
        password = pm.password();
        confirmPassword = pm.confirmPassword();
    }

    @Override
    public boolean isValid(Object obj, ConstraintValidatorContext context) {
        boolean fieldsMatch;
        String errorMessage = "Passwords do not match!";

        try {
            // get field value
            Object firstObj = BeanUtils.getProperty(obj, password);
            Object secondObj = BeanUtils.getProperty(obj, confirmPassword);

            fieldsMatch = firstObj == null && secondObj == null || firstObj != null && firstObj.equals(secondObj);

            if(!fieldsMatch) {
                context.disableDefaultConstraintViolation();
                context.buildConstraintViolationWithTemplate(errorMessage).addPropertyNode(confirmPassword).addConstraintViolation();
            }

            return fieldsMatch;

        } catch (Exception ex) {
            return false;
        }
    }

}
