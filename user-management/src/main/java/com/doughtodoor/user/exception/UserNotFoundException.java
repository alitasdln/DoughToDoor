package com.doughtodoor.user.exception;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String userNotFound) {
        super("User not found");
    }
}
