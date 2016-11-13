package ru.mail.park.responses;

/**
 * Created by Andry on 01.11.16.
 */
public class RegistrationRequest {
    private String username;
    private String password;

    @SuppressWarnings("unused")
    public RegistrationRequest() {
    }

    @SuppressWarnings("unused")
    public RegistrationRequest(String username, String password) {
        this.username = username;
        this.password = password;

    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

}
