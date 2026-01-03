package com.tbd.common.exceptions;

public class PageSizeLimitExceedException extends RuntimeException {
    public PageSizeLimitExceedException(String message) {
        super(message);
    }
}
