package com.mercedes.benz.assignment.exceptions;

public class EncryptionException extends Exception{
    public EncryptionException(String message){
        super(message);
    }
    public EncryptionException(Exception e){
        super(e);
    }
}
