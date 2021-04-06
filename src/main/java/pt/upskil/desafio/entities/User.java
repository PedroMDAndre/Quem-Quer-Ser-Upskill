package pt.upskil.desafio.entities;


import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import pt.upskil.desafio.configuration.Role;
import pt.upskil.desafio.exceptions.NoGameActiveException;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class User {
    // Attributes
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;
    private String firstName;
    private String lastName;
    private String username;
    private String city;
    private String email;
    private String password;

    @OneToMany(mappedBy = "user")
    private List<Jogo> jogos;

    //Constructors
    public User() {
    }

    public User(Long userId, String firstName, String lastName, String username, String city, String email, String password) {
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.city = city;
        this.email = email;
        this.password = password;
    }

    //Methods
    public List<GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> roles = new ArrayList<>();
        roles.add(new SimpleGrantedAuthority(Role.ROLE_USER.name()));
        return roles;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", username='" + username + '\'' +
                ", city='" + city + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    public Jogo getJogoCorrente() throws NoGameActiveException {
        for (Jogo jogo : jogos) {
            if(!jogo.isFinished()){
                return jogo;
            }
        }
        throw new NoGameActiveException();
    }
}
