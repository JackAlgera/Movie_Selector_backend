package com.jack.applications.webservice.exceptions;

import java.util.UUID;

public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException(UUID userId) {
        super(String.format("User with id %s not found.", userId.toString()));
    }
}
