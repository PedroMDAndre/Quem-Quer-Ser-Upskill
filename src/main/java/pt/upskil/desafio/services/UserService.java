package pt.upskil.desafio.services;

import org.springframework.security.core.GrantedAuthority;
import pt.upskil.desafio.configuration.Role;
import pt.upskil.desafio.entities.User;

import java.util.List;

public interface UserService {

    boolean validateUser(String username, String password);

    User currentUser();

    void addUser(User user);

    User findUser(String username);

    List<GrantedAuthority> getAuthorities(String username);

    Role getUserRole(User user);

    boolean validateUserMail(String username, String email);

    long countAllUsers();

    void save(User user);
}
