package com.dumanskyi.delivery.entities.validators;

import com.dumanskyi.delivery.entities.validators.annotations.Unique;
import com.dumanskyi.delivery.persistence.UserRepository;
import org.hibernate.AssertionFailure;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
@Component
public class UniqueFieldValidator implements ConstraintValidator<Unique, String> {

    static private UserRepository userRepository;
    private Unique annotation;
    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        UniqueFieldValidator.userRepository = userRepository;
    }


    @Override
    public void initialize(Unique constraintAnnotation) {
        annotation = constraintAnnotation;
    }

    @Override
    public boolean isValid(String field, ConstraintValidatorContext constraintValidatorContext) {
        try {
            switch (annotation.databaseField()) {
                case USERNAME:
                    return userRepository.findUserByUsernameEquals(field).isEmpty();
                case EMAIL:
                    return userRepository.findUserByEmailEquals(field).isEmpty();
            }
            throw new IllegalArgumentException("There is no such field");
        } catch (AssertionFailure ignored) {
            return true;
        }
    }
}
