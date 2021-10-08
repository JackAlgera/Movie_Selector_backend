package com.jack.applications.webservice.handlers;

import com.jack.applications.database.daos.MovieDAOImpl;
import com.jack.applications.database.resources.TMDBFilter;
import com.jack.applications.webservice.exceptions.MovieNotFoundException;
import com.jack.applications.webservice.models.Movie;
import com.jack.applications.webservice.models.Room;
import com.jack.applications.webservice.models.Selection;
import com.jack.applications.webservice.models.User;
import com.jack.applications.webservice.repos.MovieRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class MovieHandler {

    private static final Integer MAX_MOVIES_PER_REQUEST = 10;

    @Autowired
    private MovieRepo movieRepo;

    @Autowired
    private MovieDAOImpl movieDAO;

    @Autowired
    private SelectionHandler selectionHandler;

    public Movie getMovie(Integer movieId) {
        return movieRepo.findById(movieId)
            .orElseGet(() -> {
                Movie movie = movieDAO.getMovie(movieId)
                        .orElseThrow(() -> new MovieNotFoundException(movieId));
                movieRepo.save(movie);
                return movie;
            });
    }

    public List<Movie> getUnratedMoviesForUser(User user, List<TMDBFilter> filters) {
        Set<Integer> userRatedMovieIds = selectionHandler.getUserRatings(user).stream()
                            .map(Selection::getMovieId)
                            .collect(Collectors.toSet());

        List<Movie> unratedMovies = new ArrayList<>(Collections.emptyList());

        Integer p = 1;
        do {
//            List<Movie> movies = movieDAO.getMovies(filters, p);

//            movies.forEach(movie -> {
//                if (!movieRepo.existsById(movie.getId())) {
//                    movieRepo.save(movie);
//                }
//            });

            unratedMovies.addAll(movieDAO.getMovies(filters, p).stream()
                    .filter(movie -> !userRatedMovieIds.contains(movie.getId()))
                    .collect(Collectors.toList()));
            p++;
        } while (unratedMovies.size() <= MAX_MOVIES_PER_REQUEST);

        return unratedMovies.subList(0, MAX_MOVIES_PER_REQUEST);
    }

    public List<Movie> getOtherUsersUnratedMoviesForUser(User user) {
        Set<Integer> userRatedMovieIds = selectionHandler.getUserRatings(user).stream()
                .map(Selection::getMovieId)
                .collect(Collectors.toSet());

        Room room = user.getRoom();
        return room.getUserRatings().stream()
                .filter(selection -> !selection.getUserId().equals(user.getUserId()) && !userRatedMovieIds.contains(selection.getMovieId()))
                .limit(MAX_MOVIES_PER_REQUEST)
                .map(selection -> getMovie(selection.getMovieId()))
                .collect(Collectors.toList());
    }

    public boolean checkMovieExists(Integer movieId) {
        return getMovie(movieId) != null;
    }
}
