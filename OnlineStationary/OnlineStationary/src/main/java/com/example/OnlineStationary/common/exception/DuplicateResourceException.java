package com.example.OnlineStationary.common.exception;

public class DuplicateResourceException extends AppException {

    public DuplicateResourceException(String message) {
        super(message, 409);
    }

    public DuplicateResourceException(String resourceName, String fieldName, String value) {
        super(resourceName + " already exists with " + fieldName + ": " + value, 409);
    }
}