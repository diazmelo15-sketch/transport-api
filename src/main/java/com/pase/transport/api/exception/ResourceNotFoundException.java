package com.pase.transport.api.exception;

public class ResourceNotFoundException  extends RuntimeException{

    public ResourceNotFoundException(
            String message) {
        super(message);
    }
}
