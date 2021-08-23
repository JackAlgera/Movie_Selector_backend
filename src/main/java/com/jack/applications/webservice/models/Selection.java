package com.jack.applications.webservice.models;

import java.util.HashMap;
import java.util.Map;

public class Selection {

    private final String selectedMovieId;
    private final Map<String, Integer> usersAndGivenRating;
    private int totalLikes;

    public Selection(String selectedMovieId) {
        this.selectedMovieId = selectedMovieId;
        this.usersAndGivenRating = new HashMap<>();
        this.totalLikes = 0;
    }

    public void addToSelection(String userId, Integer likeRating) {
        if (usersAndGivenRating.containsKey(userId)) {
            return;
        }

        this.totalLikes++;
        this.usersAndGivenRating.put(userId, likeRating);
    }

    public String getSelectedMovieId() {
        return selectedMovieId;
    }

    public int getTotalLikes() {
        return totalLikes;
    }
}
