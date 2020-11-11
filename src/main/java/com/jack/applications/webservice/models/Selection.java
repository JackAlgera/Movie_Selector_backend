package com.jack.applications.webservice.models;

import com.jack.applications.database.models.Movie;

import java.util.HashMap;
import java.util.Map;

public class Selection {

    private Movie selectedMovie;
    private Map<String, User> usersThatLikedMovie;
    private int totalLikes;

    public Selection(Movie selectedMovie) {
        this.selectedMovie = selectedMovie;
        this.usersThatLikedMovie = new HashMap<>();
        this.totalLikes = 0;
    }

    public void addToSelection(User user) {
        this.totalLikes++;
        this.usersThatLikedMovie.put(user.getUserId(), user);
    }

    public String getSelectedMovieId() {
        return this.selectedMovie.getImdbId();
    }

    public Movie getSelectedMovie() {
        return selectedMovie;
    }

    public void setSelectedMovie(Movie selectedMovie) {
        this.selectedMovie = selectedMovie;
    }

    public Map<String, User> getUsersThatLikedMovie() {
        return usersThatLikedMovie;
    }

    public void setUsersThatLikedMovie(Map<String, User> usersThatLikedMovie) {
        this.usersThatLikedMovie = usersThatLikedMovie;
    }

    public int getTotalLikes() {
        return totalLikes;
    }

    public void setTotalLikes(int totalLikes) {
        this.totalLikes = totalLikes;
    }
}
