package com.jack.applications.web.controllers;

import com.jack.applications.database.DatabaseHandler;
import com.jack.applications.database.models.Movie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class MovieController {

    @Autowired
    private DatabaseHandler databaseHandler;

    @GetMapping(path = "/movies")
    public List<Movie> getAllMovies() {
        return databaseHandler.getAllMovies();
    }

}
