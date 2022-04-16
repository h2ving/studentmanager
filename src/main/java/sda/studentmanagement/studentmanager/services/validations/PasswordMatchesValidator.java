package sda.studentmanagement.studentmanager.services.validations;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordMatchesValidator
        implements ConstraintValidator<PasswordMatches, Object> {

    //!!!!! Do we need them? since CustomAuthenticationFilter already checks if password match and IF the DON'T server will send 403 Forbidden
    @Override
    public void initialize(PasswordMatches constraintAnnotation) {
    }
    @Override
    public boolean isValid(Object obj, ConstraintValidatorContext context){
        /*UserDto user = (UserDto) obj;
        return user.getPassword().equals(user.getMatchingPassword());*/
        return true;
    }
}