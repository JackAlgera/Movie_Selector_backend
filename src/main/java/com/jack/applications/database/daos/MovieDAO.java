package com.jack.applications.database.daos;

import com.jack.applications.database.models.Movie;

import java.util.List;

public interface MovieDAO {

    public List<Movie> getMovies();

    public Movie getMovie(String movieId);

}
