package com.jack.applications.webservice.controllers;

import com.jack.applications.webservice.handlers.RoomHandler;
import com.jack.applications.webservice.models.Room;
import com.jack.applications.webservice.models.User;
import com.jack.applications.webservice.statuscodes.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {

    @Autowired
    private RoomHandler roomHandler;

    /**
     * Get users for room.
     *
     * @param roomId
     * @return
     */
    @GetMapping(path = "/rooms/{roomId}/users")
    public ResponseEntity<List<User>> getUsersInRoom(
            @PathVariable(name = "roomId") String roomId) {
        Room room = roomHandler.getRoom(roomId);
        if(room == null) {
            throw new NotFoundException(String.format("Room with Id %s not found", roomId));
        }

        return ResponseEntity.ok(room.getConnectedUsers());
    }

    /**
     * Add new user to room.
     *
     * @param roomId
     * @param userName
     * @return
     */
    @PostMapping(path = "/rooms/{roomId}/users")
    public ResponseEntity<User> addUserToRoom(
            @PathVariable(name = "roomId") String roomId,
            @RequestParam(required = true, name = "userName") String userName,
            @RequestParam(required = true, name = "userId") String userId) {
        Room room = roomHandler.getRoom(roomId);
        if(room == null) {
            throw new NotFoundException(String.format("Room with Id %s not found", roomId));
        }

        User newUser = new User(userId, userName);
        room.addUser(newUser);
        return ResponseEntity.ok(newUser);
    }

    /**
     * Remove user from room.
     *
     * @param roomId
     * @param userId
     * @return
     */
    @DeleteMapping(path = "/rooms/{roomId}/users/{userId}")
    public ResponseEntity<User> removeUserFromRoom(
            @PathVariable(name = "roomId") String roomId,
            @PathVariable(name = "userId") String userId) {
        Room room = roomHandler.getRoom(roomId);
        if(room == null) {
            throw new NotFoundException(String.format("Room with Id %s not found", roomId));
        }
        User user = room.getUser(userId);
        if(user == null) {
            throw new NotFoundException(String.format("User with Id %s not found in room with Id %s", userId, roomId));
        }

        return ResponseEntity.ok(room.removeUser(userId));
    }
}
