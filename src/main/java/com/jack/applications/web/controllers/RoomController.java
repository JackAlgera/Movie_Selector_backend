package com.jack.applications.web.controllers;

import com.jack.applications.handlers.RoomHandler;
import com.jack.applications.pojos.Room;
import com.jack.applications.web.statuscodes.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

@RestController
public class RoomController {

    private static RoomHandler roomHandler = RoomHandler.getInstance();

    /**
     * Returns all available rooms.
     * @return
     */
    @GetMapping(path = "/rooms")
    public List<Room> getAvailableRooms() {
        return new ArrayList<Room>(roomHandler.getAvailableRooms().values());
    }

    @PostMapping(path = "/rooms")
    public ResponseEntity<Room> createNewRoom() throws URISyntaxException {
        Room newRoom = roomHandler.createNewRoom();
        return ResponseEntity.created(new URI(newRoom.getRoomId())).body(newRoom);
    }

    @DeleteMapping(path = "/rooms/{roomId}")
    public ResponseEntity<Room> deleteRoom(@PathVariable String roomId) {
        Room room = roomHandler.removeRoom(roomId);

        if(room == null) {
            throw new NotFoundException(String.format("Room with Id %s not found", roomId));
        }

        return ResponseEntity.ok(room);
    }

}
