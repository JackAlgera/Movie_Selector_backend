package com.jack.applications.webservice.models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Room {
    private final static Long MAX_TIME_BEFORE_CLOSING_ROOM = 300_000L; // 300s

    private final String roomId;
    private final Map<String, User> connectedUsers;
    private final Map<String, Selection> selectedMovies;
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

    public String foundMovieId() {
        for (Selection selection : selectedMovies.values()) {
            if (selection.getTotalLikes() > 1 && selection.getTotalLikes() >= connectedUsers.size()) {
                return selection.getSelectedMovieId();
            }
        }

        return null;
    }

    public boolean likeMovie(String selectedMovieId, String userId) {
        if (movieFound != null) {
            return false;
        }

        if (!connectedUsers.containsKey(userId)) {
            return false;
        }

        User currentUser = connectedUsers.get(userId);
        Selection currentSelection;

        if (!selectedMovies.containsKey(selectedMovieId)) {
            currentSelection = new Selection(selectedMovieId);
            selectedMovies.put(selectedMovieId, currentSelection);
            System.out.printf("Added movie with id:%s", selectedMovieId);
        } else {
            currentSelection = selectedMovies.get(selectedMovieId);
            System.out.printf("Movie with id:%s already exists. ", selectedMovieId);
        }

        currentSelection.addToSelection(currentUser);
        System.out.printf("Number of users that like this movie:%s%n", selectedMovies.get(selectedMovieId).getTotalLikes());

        return foundMovie(currentSelection);
    }

    public boolean foundMovie(Selection selection) {
        if (selection.getTotalLikes() > 1 && selection.getTotalLikes() >= getTotalConnectedUsers()) {
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

        return (System.currentTimeMillis() - timeBeforeClosingRoom) > MAX_TIME_BEFORE_CLOSING_ROOM;
    }
}
