package pt.upskil.desafio.services;

import org.springframework.ui.ModelMap;
import pt.upskil.desafio.entities.User;

public interface UserValidationService {
    UserValidationService setUser(User user);

    UserValidationService validFirstName();

    UserValidationService validLastName();

    UserValidationService validPassword();

    UserValidationService samePassword(String passwordRetyped);

    UserValidationService validEmail();

    UserValidationService validUsername();

    UserValidationService validEmail2();

    UserValidationService validCity();

    boolean isValid();

    ModelMap getErrorModelMap();

    UserValidationService clear();
}
