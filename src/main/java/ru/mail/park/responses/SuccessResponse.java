package ru.mail.park.responses;

/**
 * Created by Andry on 01.11.16.
 */
public class SuccessResponse {
    private String username;

    public SuccessResponse(String username) {
        this.username = username;
    }

    @SuppressWarnings("unused")
    public String getUsername() {
        return username;
    }
}
