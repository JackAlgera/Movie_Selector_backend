package com.jack.applications.webservice.controllers;

import com.jack.applications.database.daos.MovieDAOImpl;
import com.jack.applications.database.models.Genre;
import com.jack.applications.database.models.MovieWithoutGenres;
import com.jack.applications.database.models.Movie;
import com.jack.applications.database.resources.TMDBFilter;
import com.jack.applications.database.services.GenreHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
public class MovieController {

    @Autowired
    private MovieDAOImpl movieDAO;

    @Autowired
    private GenreHandler genreHandler;

    /**
     * Endpoint to get movie info.
     *
     * @param movieId
     * @return
     */
    @GetMapping(path = "/movies/{movieId}")
    public ResponseEntity<Movie> getMovie(@PathVariable(value = "movieId") Integer movieId) {
        Movie movie = movieDAO.getMovie(movieId);
        return movie != null ? ResponseEntity.ok(movie) : ResponseEntity.notFound().build();
    }

    /**
     * Endpoint to get list of movies. Filters can be provided, or left empty.
     *
     * @param filters
     * @return
     */
    @PostMapping(path = "/movies")
    public List<Movie> getAllMovies(@RequestBody List<TMDBFilter> filters) {
        System.out.println(filters);
        return movieDAO.getMovies(filters);
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
