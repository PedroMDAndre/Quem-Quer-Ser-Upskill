package pt.upskil.desafio.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;
import pt.upskil.desafio.entities.User;
import pt.upskil.desafio.repositories.UserRepository;

@Service
public class UserValidationServiceImpl implements UserValidationService {

    // Attributes
    @Autowired
    private UserRepository userRepository;

    private User user;
    private boolean isValid;
    private ModelMap errorModelMap;


    // Constructor
    public UserValidationServiceImpl() {
        isValid = true;
        errorModelMap = new ModelMap();
    }


    // Methods
    @Override
    public UserValidationService setUser(User user) {
        this.user = user;
        return this;
    }

    @Override
    public UserValidationService validFirstName() {
        String firstName = user.getFirstName();

        if (!firstName.matches("[A-Za-zÀ-ÿ']{2,}||[e]{1}")) {
            isValid = false;
            errorModelMap.put("errorMsgName", ErrorMessage.ERROR_MESSAGE_FIRSTNAME.getErrorMsg());
            return this;
        }
        return this;
    }
    @Override
    public UserValidationService validLastName(){
        String lastName = user.getFirstName();

        if (!lastName.matches("[A-Za-zÀ-ÿ']{2,}||[e]{1}")) {
            isValid = false;
            errorModelMap.put("errorMsgName", ErrorMessage.ERROR_MESSAGE_LASTNAME.getErrorMsg());
            return this;
        }
        return this;
    }

    @Override
    public UserValidationService validPassword() {
        if (!user.getPassword().matches(".{1,15}")) {
            isValid = false;
            errorModelMap.put("errorMsgPassword", ErrorMessage.ERROR_MESSAGE_PASSWORD.getErrorMsg());
        }
        return this;
    }

    @Override
    public UserValidationService samePassword(String passwordRetyped) {
        if (!user.getPassword().equals(passwordRetyped)) {
            isValid = false;
            errorModelMap.put("errorMsgPassword2", ErrorMessage.ERROR_MESSAGE_PASSWORD2.getErrorMsg());
        }
        return this;
    }

    @Override
    public UserValidationService validEmail() {
        User userUnique = userRepository.findByEmail(user.getEmail());
        if (userUnique != null) {
            isValid = false;
            errorModelMap.put("errorMsgEmail", ErrorMessage.ERROR_MESSAGE_EMAIL.getErrorMsg());
        }
        return this;
    }

    @Override
    public UserValidationService validUsername() {
        User userUnique = userRepository.findByUsername(user.getUsername());
        if (userUnique != null) {
            isValid = false;
            errorModelMap.put("errorMsgUsername", ErrorMessage.ERROR_MESSAGE_USERNAME.getErrorMsg());
            return this;
        }
        return this;
    }

    @Override
    public UserValidationService validEmail2() {
        if (user.getEmail().matches("^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,3}$")) {
            return this;
        }
        isValid = false;
        errorModelMap.put("errorMsgEmail", ErrorMessage.ERROR_MESSAGE_EMAIL2.getErrorMsg());
        return this;
    }

    @Override
    public UserValidationService validCity() {
        if (user.getCity().matches("^[A-Za-zÀ-ÿ'][a-zA-ZÀ-ÿ'\\s-]+[a-zA-ZÀ-ÿ']$")) {
            return this;
        }
        isValid = false;
        errorModelMap.put("errorMsgCity", ErrorMessage.ERROR_MESSAGE_CITY.getErrorMsg());
        return this;
    }

    @Override
    public boolean isValid() {
        return isValid;
    }

    @Override
    public ModelMap getErrorModelMap() {
        return errorModelMap;
    }

    @Override
    public UserValidationService clear() {
        user = null;
        isValid = true;
        errorModelMap = new ModelMap();
        return this;
    }

}
