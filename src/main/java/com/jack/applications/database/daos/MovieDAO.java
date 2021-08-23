package com.jack.applications.database.daos;

import com.jack.applications.database.models.Movie;
import com.jack.applications.database.resources.TMDBFilter;

import java.util.List;

public interface MovieDAO {

    List<Movie> getMovies(List<TMDBFilter> filters);

    Movie getMovie(Integer movieId);

}
