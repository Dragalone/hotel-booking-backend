package org.example.hotelbookingbackend.exception;

public class AlreadyBookedException extends RuntimeException{
    public AlreadyBookedException(String message){
        super(message);
    }
}
