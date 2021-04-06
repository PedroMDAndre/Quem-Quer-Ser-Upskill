package pt.upskil.desafio.services;

import pt.upskil.desafio.entities.User;

public interface RegistrationService {
    void encryptPassword(User user);

    void changeEncryptPassword(User user, String password);
}
