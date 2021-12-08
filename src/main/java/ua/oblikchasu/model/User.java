package ua.oblikchasu.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.util.List;

@Entity(name = "user")
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", updatable = false, nullable = false)
    private int id;

    @Column(name="login", updatable = false , unique = true, nullable = false, length = 32)
    private String login;

    @Column(name="pass", nullable = false, length = 64)
    private String password;

    @Column(name="name", nullable = false, length = 32)
    private String name;

    @Column(name="role", nullable = false)
    @Enumerated(EnumType.ORDINAL)
    private UserRole role;

    public User() {
    }

    public User(int id) {
        this.id = id;
    }

    public User(int id, String login, String password, String name, UserRole role) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.name = name;
        this.role = role;
    }

    public User(String login, String password, String name, UserRole role) {
        this.login = login;
        this.password = password;
        this.name = name;
        this.role = role;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public String getPassword () {
        return password;
    }

    public void setPassword (String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", login='" + login + '\'' +
                ", name='" + name + '\'' +
                ", role=" + role +
                '}';
    }
}
