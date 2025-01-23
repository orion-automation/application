package com.eorion.bo.enhancement.application.domain.exception;

public class UpdateFailedException extends Exception{
    public UpdateFailedException() {
    }

    public UpdateFailedException(String message) {
        super(message);
    }
}
