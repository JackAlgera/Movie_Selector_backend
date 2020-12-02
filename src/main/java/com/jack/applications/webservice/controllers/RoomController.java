package com.jack.applications.webservice.controllers;

import com.jack.applications.database.DatabaseHandler;
import com.jack.applications.database.models.Movie;
import com.jack.applications.webservice.handlers.RoomHandler;
import com.jack.applications.webservice.models.Room;
import com.jack.applications.webservice.models.Selection;
import com.jack.applications.webservice.models.User;
import com.jack.applications.webservice.statuscodes.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
public class RoomController {

    @Autowired
    private RoomHandler roomHandler;

    @Autowired
    private DatabaseHandler databaseHandler;

    @GetMapping(path = "/start")
    public void start() {
        databaseHandler.updateAllMovies();
    }

    /**
     * Returns all available rooms.
     * @return
     */
    @GetMapping(path = "/rooms")
    public List<Room> getAvailableRooms() {
        return new ArrayList<Room>(roomHandler.getAvailableRooms().values());
    }

    /**
     * Create new empty room.
     * @return
     * @throws URISyntaxException
     */
    @PostMapping(path = "/rooms")
    public ResponseEntity<Room> createNewRoom() throws URISyntaxException {
        Room newRoom = roomHandler.createNewRoom();
        return ResponseEntity.created(new URI(newRoom.getRoomId())).body(newRoom);
    }

    /**
     * Delete room with provided ID, throws a 404 error if room not found.
     * @param roomId
     * @return
     */
    @DeleteMapping(path = "/rooms/{roomId}")
    public ResponseEntity<Room> deleteRoom(@PathVariable String roomId) {
        Room room = roomHandler.removeRoom(roomId);

        if(room == null) {
            throw new NotFoundException(String.format("Room with Id %s not found", roomId));
        }

        return ResponseEntity.ok(room);
    }

    /**
     * Add new user to room.
     * @param roomId
     * @param userName
     * @return
     */
    @PostMapping(path = "/rooms/{roomId}/users")
    public ResponseEntity<User> addUserToRoom(
            @PathVariable(name = "roomId") String roomId,
            @RequestParam(required = true, name = "userName") String userName) {

        Room room = roomHandler.getRoom(roomId);
        if(room == null) {
            throw new NotFoundException(String.format("Room with Id %s not found", roomId));
        }

        User newUser = new User(userName);
        room.addUser(newUser);
        return ResponseEntity.ok(newUser);
    }

    /**
     * Remove user from room.
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

        room.removeUser(userId);
        return ResponseEntity.ok(user);
    }

    @GetMapping(path = "/rooms/{roomId}/foundMovie")
    public ResponseEntity<Movie> getFoundMovie(@PathVariable(name = "roomId") String roomId) {
        Room room = roomHandler.getRoom(roomId);
        if(room == null) {
            throw new NotFoundException(String.format("Room with Id %s not found", roomId));
        }

        Movie selectedMovie = null;
        Selection selection = room.getMovieFound();
        if (selection != null) {
            selectedMovie = selection.getSelectedMovie();
        }

        return ResponseEntity.ok(selectedMovie);
    }

}
