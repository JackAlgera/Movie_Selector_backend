package com.jack.applications.webservice.services;

import com.jack.applications.webservice.tmdb.resources.TMDBFilter;
import com.jack.applications.webservice.daos.MovieDAO;
import com.jack.applications.webservice.exceptions.MovieNotFoundException;
import com.jack.applications.webservice.models.Movie;
import com.jack.applications.webservice.models.Selection;
import com.jack.applications.webservice.models.User;
import com.jack.applications.webservice.repos.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class MovieService {

    private static final Integer MAX_MOVIES_PER_REQUEST = 10;

    @Autowired
    private MovieDAO movieDAO;

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private SelectionService selectionService;

    public Movie getMovie(Integer movieId) {
        return movieRepository.findById(movieId)
            .orElseGet(() -> getAndSaveMovieFromProvider(movieId));
    }

    private Movie getAndSaveMovieFromProvider(Integer movieId) {
        Movie movie = movieDAO.getMovie(movieId)
                              .orElseThrow(() -> new MovieNotFoundException(movieId));
        movieRepository.save(movie);
        return movie;
    }

    public void checkMovieExists(Integer movieId) {
        if (!movieRepository.existsById(movieId)) {
            getAndSaveMovieFromProvider(movieId);
        }
    }

    /**
     * Get movies user has not yet rated, using provided filters.
     * Filters also be left empty.
     *
     * @param filters
     * @return
     */
    public List<Movie> getUnratedMoviesForUser(User user, List<TMDBFilter> filters) {
        Set<Integer> userRatedMovieIds = selectionService.getUserRatings(user).stream()
                                                         .map(Selection::getMovieId)
                                                         .collect(Collectors.toSet());

        List<Movie> unratedMovies = new ArrayList<>(Collections.emptyList());

        Integer p = 1;
        do {
            unratedMovies.addAll(movieDAO.getMovies(filters, p).stream()
                                         .filter(movie -> !userRatedMovieIds.contains(movie.getId()))
                                         .collect(Collectors.toList()));
            p++;
        } while (unratedMovies.size() <= MAX_MOVIES_PER_REQUEST);

        return unratedMovies.subList(0, MAX_MOVIES_PER_REQUEST);
    }

    /**
     * Get movies rated by other users, that the provided user has not yet rated.
     *
     * @return
     */
    public List<Movie> getOtherUsersUnratedMoviesForUser(User user) {
        Set<Integer> userRatedMovieIds = selectionService.getUserRatings(user).stream()
                .map(Selection::getMovieId)
                .collect(Collectors.toSet());

        return selectionService.getAllRatingsForRoom(user.getRoomId()).stream()
                .filter(selection -> selection.getRating() >= 4)
                .filter(selection -> !selection.getUserId().equals(user.getUserId()) && !userRatedMovieIds.contains(selection.getMovieId()))
                .limit(MAX_MOVIES_PER_REQUEST)
                .map(selection -> getMovie(selection.getMovieId()))
                .collect(Collectors.toList());
    }
}
