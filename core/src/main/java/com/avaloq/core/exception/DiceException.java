package com.avaloq.core.exception;

public class DiceException extends Exception {

    public DiceException() {
        super();
    }

    public DiceException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public DiceException(final String message) {
        super(message);
    }

    public DiceException(final Throwable cause) {
        super(cause);
    }

    public DiceException(String s, String message, Exception e) {
    }
}
