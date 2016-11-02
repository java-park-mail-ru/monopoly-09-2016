package ru.mail.park.exception;

/**
 * Created by Andry on 02.11.16.
 */
public class JpaException extends RuntimeException {

    @SuppressWarnings("unused")
    public JpaException() {
    }

    public JpaException(String message) {
        super(message);
    }

    public JpaException(String message, Throwable cause) {
        super(message, cause);
    }

    @SuppressWarnings("unused")
    public JpaException(Throwable cause) {
        super(cause);
    }

    @SuppressWarnings("unused")
    public JpaException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
