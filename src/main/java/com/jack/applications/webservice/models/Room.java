package com.jack.applications.webservice.models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Room {
    private final static Long MAX_TIME_BEFORE_CLOSING_ROOM = 600_000L; // 600s = 10 mins

    private final String roomId;
    private final Map<String, User> connectedUsers;
    private final Map<Integer, Selection> selectedMovies;
    private Selection movieFound;
    private Long timeBeforeClosingRoom;

    public Room(String roomId) {
        this.selectedMovies = new HashMap<>();
        this.roomId = roomId;
        this.connectedUsers = new HashMap<>();
        this.movieFound = null;
        this.timeBeforeClosingRoom = -1L;
    }

    public User getUser(String userId) {
        return connectedUsers.get(userId);
    }

    public boolean likeMovie(Integer movieId, String userId, Integer likeRating) {
        if (movieFound != null) {
            return true;
        }

        if (!connectedUsers.containsKey(userId)) {
            // TODO : might need to throw 404 error here
            return false;
        }

        Selection currentSelection;

        if (!selectedMovies.containsKey(movieId)) {
            currentSelection = new Selection(movieId);
            selectedMovies.put(movieId, currentSelection);
            System.out.printf("Added movie with id:%s", movieId);
        } else {
            currentSelection = selectedMovies.get(movieId);
            System.out.printf("Movie with id:%s already exists. ", movieId);
        }

        currentSelection.addToSelection(userId, likeRating);
        System.out.printf("Number of users that like this movie:%s%n", selectedMovies.get(movieId).getTotalRatings());

        return foundMovie(currentSelection);
    }

    public boolean foundMovie(Selection selection) {
        if (selection.getTotalRatings() > 1 &&
                selection.getTotalRatings() >= getTotalConnectedUsers() &&
                selection.allRatingsAboveValue(4)) {
            movieFound = selection;
            timeBeforeClosingRoom = System.currentTimeMillis();
            return true;
        } else {
            return false;
        }
    }

    public void addUser(User newUser) {
        System.out.printf("Adding user %s to room %s%n", newUser.getUserName(), this.roomId);
        connectedUsers.put(newUser.getUserId(), newUser);
    }

    public User removeUser(String userId) {
        return connectedUsers.remove(userId);
    }

    public String getRoomId() {
        return roomId;
    }

    public List<User> getConnectedUsers() {
        return new ArrayList<>(connectedUsers.values());
    }

    public int getTotalConnectedUsers() {
        return connectedUsers.values().size();
    }

    public List<Selection> getSelectedMovies() {
        return new ArrayList<>(selectedMovies.values());
    }

    public Selection getMovieFound() {
        return movieFound;
    }

    public Long getTimeBeforeClosingRoom() {
        return System.currentTimeMillis() - timeBeforeClosingRoom;
    }

    public boolean shouldCloseRoom() {
        if (timeBeforeClosingRoom < 0) {
            return false;
        }

        return this.getTimeBeforeClosingRoom() > MAX_TIME_BEFORE_CLOSING_ROOM;
    }
}
