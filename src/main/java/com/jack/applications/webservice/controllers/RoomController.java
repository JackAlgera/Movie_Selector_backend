package com.jack.applications.webservice.controllers;

import com.jack.applications.webservice.exceptions.RoomNotFoundException;
import com.jack.applications.webservice.exceptions.UserNotFoundException;
import com.jack.applications.webservice.exceptions.statuscodes.IncorrectRequestException;
import com.jack.applications.webservice.exceptions.statuscodes.NotFoundException;
import com.jack.applications.webservice.models.Room;
import com.jack.applications.webservice.models.User;
import com.jack.applications.webservice.services.RoomService;
import com.jack.applications.webservice.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
public class RoomController {

    @Autowired
    private UserService userService;

    @Autowired
    private RoomService roomService;

    /**
     * Gets room if it exists.
     *
     * @return
     */
    @GetMapping(path = "/rooms/{roomId}")
    public ResponseEntity<Room> getRoom(@PathVariable String roomId) {
        try {
            Room room = roomService.findRoomById(roomId);
            return new ResponseEntity<>(room, HttpStatus.OK);
        } catch (RoomNotFoundException e) {
            throw new NotFoundException(e.getMessage());
        }
    }

    /**
     * Returns all available rooms.
     *
     * @return
     */
    @GetMapping(path = "/rooms")
    public List<Room> findAllRooms() {
        return new ArrayList<>(roomService.findAllRooms());
    }

    /**
     * Generate new empty room.
     *
     * @return
     */
    @PostMapping(path = "/rooms")
    public ResponseEntity<Room> generateNewRoom() {
        Room newRoom = roomService.generateNewRoom();
        return new ResponseEntity<>(newRoom, HttpStatus.CREATED);
    }

    /**
     * Delete room with provided ID, throws a 404 error if room not found.
     *
     * @param roomId
     * @return
     */
    @DeleteMapping(path = "/rooms/{roomId}")
    public ResponseEntity<?> deleteRoom(@PathVariable String roomId) {
        try {
            Room room = roomService.findRoomById(roomId);
            roomService.deleteRoom(room.getRoomId());
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (RoomNotFoundException e) {
            throw new NotFoundException(e.getMessage());
        }
    }

    /**
     * Get users for room.
     *
     * @param roomId
     * @return
     */
    @GetMapping(path = "/rooms/{roomId}/users")
    public ResponseEntity<List<User>> getUsersInRoom(@PathVariable String roomId) {
        try {
            Room room = roomService.findRoomById(roomId);
            return ResponseEntity.ok(userService.findUsersByRoomId(room.getRoomId()));
        } catch (RoomNotFoundException e) {
            throw new NotFoundException(e.getMessage());
        }
    }

    /**
     * Add user to room.
     *
     * @param roomId
     * @param userId
     * @return
     */
    @PutMapping(path = "/rooms/{roomId}/add-user")
    public ResponseEntity<User> addUserToRoom(@PathVariable String roomId, @RequestParam String userId) {
        try {
            Room room = roomService.findRoomById(roomId);
            User user = userService.findUserById(UUID.fromString(userId));
            user.setRoomId(room.getRoomId());
            room.setLastModified(Instant.now());
            roomService.updateRoom(room);
            return ResponseEntity.ok(userService.updateUser(user));
        } catch (UserNotFoundException | RoomNotFoundException e) {
            throw new NotFoundException(e.getMessage());
        } catch (IllegalArgumentException e) {
            throw new IncorrectRequestException(String.format("%s is not a valid UUID.", userId));
        }
    }

    /**
     * Returns movie that was found for the given room.
     *
     * @param roomId
     * @return
     */
    @GetMapping(path = "/rooms/{roomId}/found-movie-id")
    public ResponseEntity<Integer> getFoundMovieId(@PathVariable String roomId) {
        try {
            Room room = roomService.findRoomById(roomId);
            return ResponseEntity.ok(room.getFoundMovieId());
        } catch (UserNotFoundException | RoomNotFoundException e) {
            throw new NotFoundException(e.getMessage());
        }
    }
}
