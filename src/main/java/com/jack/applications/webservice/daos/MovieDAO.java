package com.jack.applications.webservice.daos;

import com.jack.applications.webservice.tmdb.resources.TMDBFilter;
import com.jack.applications.webservice.models.Movie;

import java.util.List;
import java.util.Optional;

public interface MovieDAO {

    List<Movie> getMovies(List<TMDBFilter> filters, Integer page);
    Optional<Movie> getMovie(Integer movieId);
}
