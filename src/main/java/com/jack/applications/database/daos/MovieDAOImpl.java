package com.jack.applications.database.daos;

import com.jack.applications.database.models.Movie;
import com.jack.applications.database.models.MovieGenre;
import com.jack.applications.utils.IdGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class MovieDAOImpl implements MovieDAO {

    @Autowired
    public IdGenerator idGenerator;

    private final Map<String, Movie> movies;

    public MovieDAOImpl() {
        this.movies = new HashMap<>();
    }

    public void addTestingMovies() {
        this.addMovie(
            new Movie(
                idGenerator.getRandomId(),
                "Movie 1",
                129L,
                MovieGenre.COMEDY,
                false
            )
        );
        this.addMovie(
            new Movie(
                idGenerator.getRandomId(),
                "Movie 2",
                129L,
                MovieGenre.HORROR,
                false
            )
        );
    }

    public void addMovie(Movie movie) {
        this.movies.put(movie.getMovieId(), movie);
    }

    public List<Movie> getMovies() {
        return new ArrayList<>(this.movies.values());
    }

    public Movie getMovie(String movieId) {
        return this.movies.get(movieId);
    }
}
