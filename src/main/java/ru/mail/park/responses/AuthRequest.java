package ru.mail.park.responses;

/**
 * Created by Andry on 01.11.16.
 */
public class AuthRequest {
    private String username;
    private String password;

    @SuppressWarnings("unused")
    public AuthRequest () {}

    @SuppressWarnings("unused")
    public AuthRequest(String username, String password) {
        this.username = username;
        this.password = password;

    }

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }

    @SuppressWarnings("unused")
    public void setPassword(String password) {
        this.password = password;
    }

    @SuppressWarnings("unused")
    public void setUsername(String username) {
        this.username = username;
    }
}
