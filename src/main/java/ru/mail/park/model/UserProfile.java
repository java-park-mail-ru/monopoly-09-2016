package ru.mail.park.model;

/**
 * Created by user on 27.09.16.
 */
public class UserProfile {
    private String name;
    private String email;
    private String password;

    public UserProfile(String email, String password, String name) {
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public String getEmail() { return email; }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }
}
