package com.eorion.bo.enhancement.application.domain.exception;

public class DataNotExistException extends Exception{
    public DataNotExistException() {
    }

    public DataNotExistException(String message) {
        super(message);
    }
}
