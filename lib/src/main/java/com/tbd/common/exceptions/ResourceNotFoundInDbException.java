package com.tbd.common.exceptions;

public class ResourceNotFoundInDbException extends RuntimeException {
    public ResourceNotFoundInDbException(String message) {
        super(message);
    }
}
