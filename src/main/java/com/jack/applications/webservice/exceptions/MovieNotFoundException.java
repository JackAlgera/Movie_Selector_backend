package com.jack.applications.webservice.exceptions;

public class MovieNotFoundException extends RuntimeException {

    public MovieNotFoundException(Integer movieId) {
        super(String.format("Movie with id %s not found.", movieId));
    }
}
