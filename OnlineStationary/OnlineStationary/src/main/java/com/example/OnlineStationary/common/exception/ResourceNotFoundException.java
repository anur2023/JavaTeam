package com.example.OnlineStationary.common.exception;

public class ResourceNotFoundException extends AppException {

    public ResourceNotFoundException(String message) {
        super(message, 404);
    }

    public ResourceNotFoundException(String resourceName, Long id) {
        super(resourceName + " not found with id: " + id, 404);
    }
}