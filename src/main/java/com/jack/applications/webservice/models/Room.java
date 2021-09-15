package com.jack.applications.webservice.models;

import com.jack.applications.webservice.statuscodes.IncorrectRequestException;
import com.jack.applications.webservice.statuscodes.NotFoundException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
        this.timeBeforeClosingRoom = System.currentTimeMillis();
    }

    public User getUser(String userId) {
        return connectedUsers.get(userId);
    }

    public boolean likeMovie(Integer movieId, String userId, Integer likeRating) {
        timeBeforeClosingRoom = System.currentTimeMillis();

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

    public List<Integer> getUnratedMoviesForUser(String userId, Integer maxMoviesPerRequest) {
        if (maxMoviesPerRequest < 0) {
            throw new IncorrectRequestException("maxMoviesPerRequest needs to be greater that 0.");
        }

        if (!connectedUsers.containsKey(userId)) {
            throw new NotFoundException(String.format("User with Id %s not in room %s", userId, roomId));
        }

        return selectedMovies.values().stream()
                .filter(selection -> !selection.userLikedMovie(userId))
                .limit(maxMoviesPerRequest)
                .map(Selection::getSelectedMovieId)
                .collect(Collectors.toList());
    }

    public boolean checkIfUserRatedMovie(String userId, Integer movieId) {
        if (!selectedMovies.containsKey(movieId)) {
            return false;
        }

        return selectedMovies.get(movieId).userLikedMovie(userId);
    }

    public Selection getMovieFound() {
        return movieFound;
    }

    public Long getTimeBeforeClosingRoom() {
        return System.currentTimeMillis() - timeBeforeClosingRoom;
    }

    public boolean shouldCloseRoom() {
        return this.getTimeBeforeClosingRoom() > MAX_TIME_BEFORE_CLOSING_ROOM;
    }
}
