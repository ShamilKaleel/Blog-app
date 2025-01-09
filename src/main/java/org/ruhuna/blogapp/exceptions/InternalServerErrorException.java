package org.ruhuna.blogapp.exceptions;

public class InternalServerErrorException extends ResourceNotFoundException{

    public InternalServerErrorException(String message) {
        super(message);
    }
}
