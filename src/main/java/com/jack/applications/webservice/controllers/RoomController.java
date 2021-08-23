package com.jack.applications.webservice.controllers;

import com.jack.applications.database.daos.MovieDAOImpl;
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
    private MovieDAOImpl movieDAO;

    /**
     * Returns all available rooms.
     *
     * @return
     */
    @GetMapping(path = "/rooms")
    public List<Room> getAvailableRooms() {
        return new ArrayList<>(roomHandler.getAvailableRooms());
    }

    /**
     * Gets room if it exists.
     *
     * @return
     */
    @GetMapping(path = "/rooms/{roomId}")
    public ResponseEntity<Room> getRoom(@PathVariable String roomId) {
        Room room = roomHandler.getRoom(roomId);
        return room == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(room);
    }

    /**
     * Create new empty room.
     *
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
     *
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

    @GetMapping(path = "/rooms/{roomId}/foundMovie")
    public ResponseEntity<Movie> getFoundMovie(@PathVariable(name = "roomId") String roomId) {
        Room room = roomHandler.getRoom(roomId);
        if(room == null) {
            throw new NotFoundException(String.format("Room with Id %s not found", roomId));
        }

        Selection selection = room.getMovieFound();
        return null; // (selection != null) ? ResponseEntity.ok(movieDAO.getMovie(selection.getSelectedMovieId())) : null;
    }

    /**
     * Endpoint to like a movie in a room.
     *
     * @param roomId
     * @param movieId
     * @param userId
     * @param likeRating
     * @return
     */
    @PostMapping(path = "/rooms/{roomId}/movies/{movieId}/like")
    public ResponseEntity<Boolean> likeMovie(@PathVariable String roomId,
                                             @PathVariable String movieId,
                                             @RequestParam(required = true, name = "userId") String userId,
                                             @RequestParam(required = true, name = "likeRating") Integer likeRating) {
        Room room = roomHandler.getRoom(roomId);
        if (room == null) {
            throw new NotFoundException(String.format("Room with Id %s not found", roomId));
        }

        User user = room.getUser(userId);
        if (user == null) {
            throw new NotFoundException(String.format("User with Id %s in room with Id %s not found", userId, roomId));
        }

        return ResponseEntity.ok(room.likeMovie(movieId, userId, likeRating));
    }
}
