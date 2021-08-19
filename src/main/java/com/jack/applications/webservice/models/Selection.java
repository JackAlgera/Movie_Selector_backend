package com.jack.applications.webservice.models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Selection {

    private final String selectedMovieId;
    private final Map<String, User> usersThatLikedMovie;
    private int totalLikes;

    public Selection(String selectedMovieId) {
        this.selectedMovieId = selectedMovieId;
        this.usersThatLikedMovie = new HashMap<>();
        this.totalLikes = 0;
    }

    public void addToSelection(User user) {
        if (usersThatLikedMovie.containsKey(user.getUserId())) {
            return;
        }

        this.totalLikes++;
        this.usersThatLikedMovie.put(user.getUserId(), user);
    }

    public String getSelectedMovieId() {
        return selectedMovieId;
    }

    public List<User> getUsersThatLikedMovie() {
        return new ArrayList<>(usersThatLikedMovie.values());
    }

    public int getTotalLikes() {
        return totalLikes;
    }
}
