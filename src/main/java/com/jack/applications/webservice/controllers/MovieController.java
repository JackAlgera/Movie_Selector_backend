package com.jack.applications.webservice.controllers;

import com.jack.applications.database.daos.MovieDAOImpl;
import com.jack.applications.database.models.Genre;
import com.jack.applications.database.models.Movie;
import com.jack.applications.database.resources.TMDBConstants;
import com.jack.applications.database.resources.TMDBFilter;
import com.jack.applications.database.resources.TMDBFilterKeys;
import com.jack.applications.database.services.GenreHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.InputStream;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
public class MovieController {

    @Autowired
    private MovieDAOImpl movieDAO;

    @Autowired
    private GenreHandler genreHandler;

    @PostMapping(path = "/movies")
    public List<Movie> getAllMovies(@RequestBody List<TMDBFilter> filters) {
        System.out.println(filters);
        return movieDAO.getMovies(filters);
    }

    @GetMapping(path = "/genres")
    public List<Genre> getAllGenres() {
        return genreHandler.getAllGenres();
    }
}
