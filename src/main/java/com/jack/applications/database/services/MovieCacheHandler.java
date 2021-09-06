package com.jack.applications.database.services;

import com.jack.applications.database.models.Movie;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class MovieCacheHandler {

    private final Map<Integer, Movie> cachedMovies;

    public MovieCacheHandler() {
        this.cachedMovies = new HashMap<>();
    }

    public void cacheMovie(Movie movie) {
        cachedMovies.put(movie.getId(), movie);
    }

    public Movie getCachedMovie(Integer movieId) {
        return cachedMovies.get(movieId);
    }

    public boolean isMovieAlreadyCached(Integer movieId) {
        return cachedMovies.containsKey(movieId);
    }
}
