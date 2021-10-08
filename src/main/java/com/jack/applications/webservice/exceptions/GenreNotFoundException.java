package com.jack.applications.webservice.exceptions;

public class GenreNotFoundException extends RuntimeException {

    public GenreNotFoundException(Integer genreId) {
        super(String.format("Genre with id %s not found.", genreId));
    }
}
