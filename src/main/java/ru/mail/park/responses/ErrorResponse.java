package ru.mail.park.responses;

/**
 * Created by Andry on 01.11.16.
 */
public class ErrorResponse {
    private String message;

    public ErrorResponse(String message){ this.message = message; }

    @SuppressWarnings("unused")
    public String getMessage() {
        return message;
    }
}
