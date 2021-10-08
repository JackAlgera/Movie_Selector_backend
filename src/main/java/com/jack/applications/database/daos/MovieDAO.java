package com.jack.applications.database.daos;

import com.jack.applications.webservice.models.Movie;
import com.jack.applications.database.resources.TMDBFilter;

import java.util.List;
import java.util.Optional;

public interface MovieDAO {

    List<Movie> getMovies(List<TMDBFilter> filters, Integer page);

    Optional<Movie> getMovie(Integer movieId);
}
