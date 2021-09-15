package com.jack.applications.webservice.controllers;

import com.jack.applications.database.daos.MovieDAOImpl;
import com.jack.applications.database.models.Genre;
import com.jack.applications.database.models.Movie;
import com.jack.applications.database.resources.TMDBFilter;
import com.jack.applications.database.services.GenreHandler;
import com.jack.applications.webservice.handlers.RoomHandler;
import com.jack.applications.webservice.models.Room;
import com.jack.applications.webservice.models.User;
import com.jack.applications.webservice.statuscodes.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
public class MovieController {

    private static final Integer MAX_MOVIES_PER_REQUEST = 10;

    @Autowired
    private MovieDAOImpl movieDAO;

    @Autowired
    private GenreHandler genreHandler;

    @Autowired
    private RoomHandler roomHandler;

    /**
     * Endpoint to get movie info.
     *
     * @param movieId
     * @return
     */
    @GetMapping(path = "/movies/{movieId}")
    public ResponseEntity<Movie> getMovie(@PathVariable Integer movieId) {
        Movie movie = movieDAO.getMovie(movieId);
        return movie != null ? ResponseEntity.ok(movie) : ResponseEntity.notFound().build();
    }

    /**
     * Endpoint to get list of movies filtering out movies the user has already seen.
     * Filters can be provided, or left empty.
     *
     * @param filters
     * @return
     */
    @PostMapping(path = "/room/{roomId}/users/{userId}/movies")
    public List<Movie> getAllMovies(@RequestBody List<TMDBFilter> filters,
                                    @PathVariable String roomId,
                                    @PathVariable String userId) {
        Room room = roomHandler.getRoom(roomId);
        if(room == null) {
            throw new NotFoundException(String.format("Room with Id %s not found", roomId));
        }

        User user = room.getUser(userId);
        if (user == null) {
            throw new NotFoundException(String.format("User with Id %s in room with Id %s not found", userId, roomId));
        }

        return movieDAO.getMovies(filters).stream()
                .filter(movie -> !room.checkIfUserRatedMovie(userId, movie.getId()))
                .collect(Collectors.toList());
    }

    /**
     * Endpoints that gets movies the user has not yet seen.
     *
     * @param roomId
     * @param userId
     * @return
     */
    @GetMapping(path = "/room/{roomId}/users/{userId}/unrated-movies")
    public List<Movie> getUnratedMoviesByUser(@PathVariable String roomId,
                                              @PathVariable String userId,
                                              @RequestParam(name = "maxMoviesPerRequest") Integer maxMoviesPerRequest) {
        Room room = roomHandler.getRoom(roomId);
        if(room == null) {
            throw new NotFoundException(String.format("Room with Id %s not found", roomId));
        }

        return room.getUnratedMoviesForUser(userId, maxMoviesPerRequest)
                .stream()
                .map(movieDAO::getMovie)
                .collect(Collectors.toList());
    }

    /**
     * Endpoint to get list of all available genres from the platform.
     *
     * @return
     */
    @GetMapping(path = "/genres")
    public List<Genre> getAllGenres() {
        return genreHandler.getAllGenres();
    }
}
