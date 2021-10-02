package com.mercedes.benz.assignment.services;

import com.mercedes.benz.assignment.exceptions.EncryptionException;

public interface EncryptionService {
    String encrypt(String message) throws EncryptionException;
    String decrypt(String encrypted) throws Exception;
}
