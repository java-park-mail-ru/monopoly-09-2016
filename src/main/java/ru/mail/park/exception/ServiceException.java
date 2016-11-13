package ru.mail.park.exception;

/**
 * Created by Andry on 13.11.16.
 */
public class ServiceException extends JpaException {
    private static final String MESSAGE = "Service is not available";

    @SuppressWarnings("unused")
    public ServiceException() {
        super(MESSAGE);
    }

    public ServiceException(Throwable cause) {
        super(MESSAGE, cause);
    }
}
