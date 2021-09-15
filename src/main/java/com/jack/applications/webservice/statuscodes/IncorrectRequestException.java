package com.jack.applications.webservice.statuscodes;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class IncorrectRequestException extends ResponseStatusException {

    public IncorrectRequestException(String reason) {
        super(HttpStatus.BAD_REQUEST, reason);
    }
}
