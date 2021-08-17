package com.jack.applications.webservice.controllers;

import com.jack.applications.database.daos.MovieDAOImpl;
import com.jack.applications.database.models.Movie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
public class MovieController {

    @Autowired
    private MovieDAOImpl movieDAO;

    @GetMapping(path = "/start")
    public void addTestingMovies() {
        movieDAO.addTestingMovies();
    }

    @GetMapping(path = "/movies")
    public List<Movie> getAllMovies() {
        return movieDAO.getMovies();
    }

}
