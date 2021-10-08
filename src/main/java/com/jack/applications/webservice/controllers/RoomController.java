package com.jack.applications.webservice.controllers;

import com.jack.applications.webservice.exceptions.RoomNotFoundException;
import com.jack.applications.webservice.exceptions.UserNotFoundException;
import com.jack.applications.webservice.handlers.RoomHandler;
import com.jack.applications.webservice.handlers.UserHandler;
import com.jack.applications.webservice.models.Room;
import com.jack.applications.webservice.models.User;
import com.jack.applications.webservice.exceptions.statuscodes.IncorrectRequestException;
import com.jack.applications.webservice.exceptions.statuscodes.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
public class RoomController {

    @Autowired
    private UserHandler userHandler;

    @Autowired
    private RoomHandler roomHandler;

//    @Autowired
//    private MovieDAOImpl movieDAO;

    /**
     * Gets room if it exists.
     *
     * @return
     */
    @GetMapping(path = "/rooms/{roomId}")
    public ResponseEntity<Room> getRoom(@PathVariable String roomId) {
        try {
            Room room = roomHandler.findRoomById(roomId);
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
        return new ArrayList<>(roomHandler.findAllRooms());
    }

    /**
     * Generate new empty room.
     *
     * @return
     */
    @PostMapping(path = "/rooms")
    public ResponseEntity<Room> generateNewRoom() {
        Room newRoom = roomHandler.generateNewRoom();
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
        roomHandler.deleteRoom(roomId);
        return new ResponseEntity<>(HttpStatus.OK);
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
            Room room = roomHandler.findRoomById(roomId);
            return ResponseEntity.ok(room.getConnectedUsers());
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
            Room room = roomHandler.findRoomById(roomId);
            User user = userHandler.findUserById(UUID.fromString(userId));
            user.setRoom(room);
            return ResponseEntity.ok(userHandler.updateUser(user));
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
            Room room = roomHandler.findRoomById(roomId);
            return ResponseEntity.ok(room.getFoundMovieId());
        } catch (UserNotFoundException | RoomNotFoundException e) {
            throw new NotFoundException(e.getMessage());
        }
    }
}
