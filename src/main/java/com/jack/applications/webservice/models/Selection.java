package com.jack.applications.webservice.models;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public class Selection {

    private final Integer selectedMovieId;
    private final Map<String, Integer> usersAndGivenRating;
    private int totalRatings;

    public Selection(Integer selectedMovieId) {
        this.selectedMovieId = selectedMovieId;
        this.usersAndGivenRating = new HashMap<>();
        this.totalRatings = 0;
    }

    public void addToSelection(String userId, Integer likeRating) {
        if (usersAndGivenRating.containsKey(userId)) {
            return;
        }

        this.totalRatings++;
        this.usersAndGivenRating.put(userId, likeRating);
    }

    public boolean allRatingsAboveValue(Integer val) {
        return usersAndGivenRating.values().stream().noneMatch(rating -> rating < val);
    }

    public boolean userLikedMovie(String userId) {
        return usersAndGivenRating.containsKey(userId);
    }
}
