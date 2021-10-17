package com.jack.applications.webservice.controllers;

import com.jack.applications.webservice.exceptions.MovieAlreadyFoundForRoomException;
import com.jack.applications.webservice.exceptions.MovieNotFoundException;
import com.jack.applications.webservice.exceptions.UserNotFoundException;
import com.jack.applications.webservice.exceptions.statuscodes.ConflictRequestException;
import com.jack.applications.webservice.exceptions.statuscodes.IncorrectRequestException;
import com.jack.applications.webservice.exceptions.statuscodes.NotFoundException;
import com.jack.applications.webservice.models.Selection;
import com.jack.applications.webservice.models.User;
import com.jack.applications.webservice.services.MovieService;
import com.jack.applications.webservice.services.SelectionService;
import com.jack.applications.webservice.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
public class UserController {

    @Autowired
    private SelectionService selectionService;

    @Autowired
    private MovieService movieService;

    @Autowired
    private UserService userService;

    /**
     * Get user.
     *
     * @param userId
     * @return
     */
    @GetMapping(path = "/users/{userId}")
    public ResponseEntity<User> getUserById(@PathVariable String userId) {
        try {
            User user = userService.findUserById(UUID.fromString(userId));
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
        List<User> users = userService.findAllUsers();
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
        User newUser = userService.createUser(user);
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
            User updatedUser = userService.updateUser(user);
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
            User user = userService.findUserById(UUID.fromString(userId));
            userService.deleteUser(user.getUserId());
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (UserNotFoundException e) {
            throw new NotFoundException(e.getMessage());
        }
        catch (IllegalArgumentException e) {
            throw new IncorrectRequestException(String.format("%s is not a valid UUID.", userId));
        }
    }

    /**
     * Allows a user to rate a movie. Will throw a 404 error if the user is not part of a room.
     *
     * @param userId
     * @param movieId
     * @param rating
     * @return
     */
    @PostMapping(path = "/users/{userId}/rate-movie")
    public ResponseEntity<?> rateMovie(@PathVariable String userId,
                                       @RequestParam Integer movieId,
                                       @RequestParam Integer rating) {
        try {
            if (!selectionService.isCorrectRating(rating)) {
                throw new IncorrectRequestException(String.format("Rating needs to be 5 >= rating (%d) >= 1.", rating));
            }

            User user = userService.findUserById(UUID.fromString(userId));
            if (user.getRoomId() == null) {
                throw new IncorrectRequestException(String.format("User with Id %s not in room.", userId));
            }

            movieService.checkMovieExists(movieId);
            selectionService.rateMovie(user, movieId, rating);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (UserNotFoundException | MovieNotFoundException e) {
            throw new NotFoundException(e.getMessage());
        } catch (IllegalArgumentException e) {
            throw new IncorrectRequestException(String.format("%s is not a valid UUID.", userId));
        } catch (MovieAlreadyFoundForRoomException e) {
            throw new ConflictRequestException(e.getMessage());
        }
    }

    /**
     * Get list of all user ratings.
     *
     * @param userId
     * @return
     */
    @GetMapping(path = "/users/{userId}/rated-movies")
    public ResponseEntity<List<Selection>> getRatedMovies(@PathVariable String userId) {
        try {
            User user = userService.findUserById(UUID.fromString(userId));
            if (user.getRoomId() == null) {
                throw new IncorrectRequestException(String.format("User with Id %s not in room.", userId));
            }
            List<Selection> selections = selectionService.getUserRatings(user);
            return new ResponseEntity<>(selections, HttpStatus.OK);
        } catch (UserNotFoundException e) {
            throw new NotFoundException(e.getMessage());
        } catch (IllegalArgumentException e) {
            throw new IncorrectRequestException(String.format("%s is not a valid UUID.", userId));
        }
    }
}
