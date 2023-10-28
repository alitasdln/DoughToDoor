package com.doughtodoor.user.exception;

public class UserAlreadyExistsException extends RuntimeException {
    public UserAlreadyExistsException(String usernameIsAlreadyTaken) {
        super("Username already exists.");
    }
}
