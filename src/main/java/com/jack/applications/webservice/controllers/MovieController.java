package com.jack.applications.webservice.controllers;

import com.jack.applications.webservice.tmdb.resources.TMDBFilter;
import com.jack.applications.webservice.exceptions.GenreNotFoundException;
import com.jack.applications.webservice.exceptions.MovieNotFoundException;
import com.jack.applications.webservice.exceptions.UserNotFoundException;
import com.jack.applications.webservice.exceptions.statuscodes.IncorrectRequestException;
import com.jack.applications.webservice.exceptions.statuscodes.NotFoundException;
import com.jack.applications.webservice.models.Genre;
import com.jack.applications.webservice.models.Movie;
import com.jack.applications.webservice.models.User;
import com.jack.applications.webservice.services.GenreService;
import com.jack.applications.webservice.services.MovieService;
import com.jack.applications.webservice.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
public class MovieController {

    @Autowired
    private MovieService movieService;

    @Autowired
    private GenreService genreHandler;

    @Autowired
    private UserService userService;

    /**
     * Endpoint to get list of all available genres from the platform.
     *
     * @return
     */
    @GetMapping(path = "/genres")
    public ResponseEntity<List<Genre>> getAllGenres() {
        try {
            List<Genre> genres = genreHandler.getAllGenres();
            return new ResponseEntity<>(genres, HttpStatus.OK);
        } catch (GenreNotFoundException e) {
            throw new IncorrectRequestException(e.getMessage());
        }
    }

    /**
     * Endpoint to get movie info.
     *
     * @param movieId
     * @return
     */
    @GetMapping(path = "/movies/{movieId}")
    public ResponseEntity<Movie> getMovie(@PathVariable Integer movieId) {
        try {
            Movie movie = movieService.getMovie(movieId);
            return new ResponseEntity<>(movie, HttpStatus.OK);
        } catch (MovieNotFoundException e) {
            throw new NotFoundException(e.getMessage());
        }
    }

    /**
     * Get movies user has not yet rated, using provided filters.
     * Filters also be left empty.
     *
     * @param filters
     * @return
     */
    @PostMapping(path = "/users/{userId}/unrated-movies")
    public ResponseEntity<List<Movie>> getUnratedMoviesForUser(@PathVariable String userId, @RequestBody List<TMDBFilter> filters) {
        try {
            User user = userService.findUserById(UUID.fromString(userId));
            List<Movie> movies = movieService.getUnratedMoviesForUser(user, filters);
            return new ResponseEntity<>(movies, HttpStatus.OK);
        } catch (UserNotFoundException | MovieNotFoundException e) {
            throw new NotFoundException(e.getMessage());
        } catch (IllegalArgumentException e) {
            throw new IncorrectRequestException(String.format("%s is not a valid UUID.", userId));
        }
    }

    /**
     * Get movies rated by other users, that the provided user has not yet rated.
     *
     * @return
     */
    @PostMapping(path = "/users/{userId}/other-users-unrated-movies")
    public ResponseEntity<List<Movie>> getOtherUsersUnratedMoviesForUser(@PathVariable String userId) {
        try {
            User user = userService.findUserById(UUID.fromString(userId));
            List<Movie> movies = movieService.getOtherUsersUnratedMoviesForUser(user);
            return new ResponseEntity<>(movies, HttpStatus.OK);
        } catch (UserNotFoundException | MovieNotFoundException e) {
            throw new NotFoundException(e.getMessage());
        } catch (IllegalArgumentException e) {
            throw new IncorrectRequestException(String.format("%s is not a valid UUID.", userId));
        }
    }
}
