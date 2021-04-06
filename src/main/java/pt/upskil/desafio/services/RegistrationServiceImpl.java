package pt.upskil.desafio.services;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import pt.upskil.desafio.entities.User;

@Service
public class RegistrationServiceImpl implements RegistrationService {
    public void encryptPassword(User user){
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        user.setPassword(encoder.encode(user.getPassword()));
    }
    public void changeEncryptPassword(User user, String password){
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        user.setPassword(encoder.encode(password));
    }

}
