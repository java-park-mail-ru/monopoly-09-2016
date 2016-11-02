package ru.mail.park.exception;

/**
 * Created by Andry on 02.11.16.
 */
public class UserExistsException extends JpaException {
    private static final String MESSAGE = "User already exists";

    @SuppressWarnings("unused")
    public UserExistsException() {
        super(MESSAGE);
    }

    public UserExistsException(Throwable cause) {
        super(MESSAGE, cause);
    }
}
