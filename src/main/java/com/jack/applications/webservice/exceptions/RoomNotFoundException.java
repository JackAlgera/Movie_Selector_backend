package com.jack.applications.webservice.exceptions;

public class RoomNotFoundException extends RuntimeException {

    public RoomNotFoundException(String roomId) {
        super(String.format("Room with id %s not found.", roomId));
    }
}
