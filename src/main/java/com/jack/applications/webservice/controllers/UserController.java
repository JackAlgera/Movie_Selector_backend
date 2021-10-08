package com.jack.applications.webservice.controllers;

import com.jack.applications.webservice.exceptions.MovieAlreadyFoundForRoomException;
import com.jack.applications.webservice.exceptions.MovieNotFoundException;
import com.jack.applications.webservice.exceptions.UserNotFoundException;
import com.jack.applications.webservice.exceptions.statuscodes.IncorrectRequestException;
import com.jack.applications.webservice.exceptions.statuscodes.NotFoundException;
import com.jack.applications.webservice.handlers.MovieHandler;
import com.jack.applications.webservice.handlers.SelectionHandler;
import com.jack.applications.webservice.handlers.UserHandler;
import com.jack.applications.webservice.models.Selection;
import com.jack.applications.webservice.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
public class UserController {

    @Autowired
    private SelectionHandler selectionHandler;

    @Autowired
    private MovieHandler movieHandler;

    @Autowired
    private UserHandler userHandler;

    /**
     * Get user.
     *
     * @param userId
     * @return
     */
    @GetMapping(path = "/users/{userId}")
    public ResponseEntity<User> getUserById(@PathVariable String userId) {
        try {
            User user = userHandler.findUserById(UUID.fromString(userId));
            return new ResponseEntity<>(user, HttpStatus.OK);
        } catch (UserNotFoundException e) {
            throw new NotFoundException(e.getMessage());
        } catch (IllegalArgumentException e) {
            throw new IncorrectRequestException(String.format("%s is not a valid UUID.", userId));
        }
    }

    /**
     * Get all users.
     *
     * @return
     */
    @GetMapping(path = "/users")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userHandler.findAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    /**
     * Generates a new user. This should be stored in cache for future use on client-side.
     *
     * @param user
     * @return
     */
    @PostMapping(path = "/users")
    public ResponseEntity<User> addUser(@RequestBody User user) {
        User newUser = userHandler.createUser(user);
        return new ResponseEntity<>(newUser, HttpStatus.CREATED);
    }

    /**
     * Updates a user. This should be stored in cache for future use on client-side.
     *
     * @param user
     * @return
     */
    @PutMapping(path = "/users")
    public ResponseEntity<User> updateUser(@RequestBody User user) {
        try {
            User updatedUser = userHandler.updateUser(user);
            return new ResponseEntity<>(updatedUser, HttpStatus.OK);
        } catch (UserNotFoundException e) {
            throw new NotFoundException(e.getMessage());
        }
    }

    /**
     * Deletes a user.
     *
     * @param userId
     * @return
     */
    @DeleteMapping(path = "/users/{userId}")
    public ResponseEntity<?> deleteUser(@PathVariable String userId) {
        try {
            User user = userHandler.findUserById(UUID.fromString(userId));
            selectionHandler.deleteAllUserRatings(user);
            userHandler.deleteUser(user.getUserId());
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (UserNotFoundException e) {
            throw new NotFoundException(e.getMessage());
        }
        catch (IllegalArgumentException e) {
            throw new IncorrectRequestException(String.format("%s is not a valid UUID.", userId));
        }
    }

    @PostMapping(path = "/users/{userId}/rate-movie")
    public ResponseEntity<?> rateMovie(@PathVariable String userId,
                                       @RequestParam Integer movieId,
                                       @RequestParam Integer rating) {
        try {
            if (!selectionHandler.isCorrectRating(rating)) {
                throw new IncorrectRequestException(String.format("Rating needs to be 5 >= rating (%d) >= 1.", rating));
            }

            User user = userHandler.findUserById(UUID.fromString(userId));
            if (user.getRoom() == null) {
                throw new IncorrectRequestException(String.format("User with Id %s not in room.", userId));
            }
            movieHandler.checkMovieExists(movieId);
            selectionHandler.rateMovie(user, movieId, rating);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (UserNotFoundException | MovieNotFoundException e) {
            throw new NotFoundException(e.getMessage());
        } catch (IllegalArgumentException e) {
            throw new IncorrectRequestException(String.format("%s is not a valid UUID.", userId));
        } catch (MovieAlreadyFoundForRoomException e) {
            throw new IncorrectRequestException(e.getMessage());
        }
    }

    @GetMapping(path = "/users/{userId}/rated-movies")
    public ResponseEntity<List<Selection>> getRatedMovies(@PathVariable String userId) {
        try {
            User user = userHandler.findUserById(UUID.fromString(userId));
            if (user.getRoom() == null) {
                throw new IncorrectRequestException(String.format("User with Id %s not in room.", userId));
            }
            List<Selection> selections = selectionHandler.getUserRatings(user);
            return new ResponseEntity<>(selections, HttpStatus.OK);
        } catch (UserNotFoundException e) {
            throw new NotFoundException(e.getMessage());
        } catch (IllegalArgumentException e) {
            throw new IncorrectRequestException(String.format("%s is not a valid UUID.", userId));
        }
    }
}
