package com.homebrew.coffee.haushaltsbuch.persistence;

public class DataAlreadyExists extends Exception {

    private static final long serialVersionUID = 1997753363232907009L;

    public DataAlreadyExists() {
    }

    public DataAlreadyExists(String message) {
        super(message);
    }

    public DataAlreadyExists(Throwable cause) {
        super(cause);
    }

    public DataAlreadyExists(String message, Throwable cause) {
        super(message, cause);
    }

    public DataAlreadyExists(String message, Throwable cause,
                           boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}

