package com.jack.applications.webservice.exceptions;

public class MovieAlreadyFoundForRoomException extends RuntimeException {

    public MovieAlreadyFoundForRoomException(String roomId, Integer movieId) {
        super(String.format("Room with id %s already found movie with Id %d.", roomId, movieId));
    }
}
