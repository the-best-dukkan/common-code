package com.tbd.common.exceptions;

public class UserSubNotFoundInHeaderException extends RuntimeException {
    public UserSubNotFoundInHeaderException(String message) {
        super(message);
    }
}
