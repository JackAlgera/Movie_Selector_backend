package com.jack.applications.webservice.models;

import com.jack.applications.database.models.Movie;
import com.jack.applications.utils.IdGenerator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Room {

    private final static IdGenerator idGenerator = IdGenerator.getIdGenerator();

    private String roomId;
    private Map<String, User> connectedUsers;
    private final Map<String, Selection> selectedMovies;
    private Selection movieFound;

    public Room() {
        this.selectedMovies = new HashMap<>();
        this.roomId = idGenerator.getRandomId();
        this.connectedUsers = new HashMap<>();
        this.movieFound = null;
    }

    public User getUser(String userId) {
        for (User user : connectedUsers.values()) {
            if (user.getUserId().equals(userId)) {
                return user;
            }
        }
        return null;
    }

    public String getFoundMovieId() {
        for (Selection selection : selectedMovies.values()) {
            if (selection.getTotalLikes() > 1 && selection.getTotalLikes() == connectedUsers.size()) {
                return selection.getSelectedMovieId();
            }
        }

        return null;
    }

    public boolean likeMovie(Movie selectedMovie, String userId, int numberOfUsersConnected) {
        if (movieFound != null) {
            return false;
        }

        if (!connectedUsers.containsKey(userId)) {
            return false;
        }
        User currentUser = connectedUsers.get(userId);

        if (!selectedMovies.containsKey(selectedMovie.getImdbId())) {
            Selection newSelection = new Selection(selectedMovie);
            newSelection.addToSelection(currentUser);
            selectedMovies.put(selectedMovie.getImdbId(), newSelection);

            System.out.println(String.format("Added movie with id:%s, number of users that like this movie:%s",
                    selectedMovie.getImdbId(),
                    selectedMovies.get(selectedMovie.getImdbId()).getTotalLikes()));
            return false;
        } else {
            Selection existingSelection = selectedMovies.get(selectedMovie.getImdbId());
            existingSelection.addToSelection(currentUser);
            System.out.println(String.format("Movie with id:%s already exists, number of users that like this movie:%s",
                    selectedMovie.getImdbId(),
                    selectedMovies.get(selectedMovie.getImdbId()).getTotalLikes()));
            if (existingSelection.getTotalLikes() > 1 && existingSelection.getTotalLikes() >= numberOfUsersConnected) {
                movieFound = existingSelection;
                return true;
            } else {
                return false;
            }
        }
    }

    public void addUser(User newUser) {
        this.connectedUsers.put(newUser.getUserId(), newUser);
    }

    public void removeUser(String userId) {
        System.out.println("Removing user: " + userId);
        User removedUser = this.connectedUsers.remove(userId);
        System.out.println("Removed user: " + removedUser.toString());
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public Map<String, User> getConnectedUsers() {
        return connectedUsers;
    }

    public Map<String, Selection> getSelectedMovies() {
        return selectedMovies;
    }

    public Selection getMovieFound() {
        return movieFound;
    }

    @Override
    public String toString() {
        return "Room{" +
                "roomId='" + roomId + '\'' +
                ", connectedUsers=" + connectedUsers +
                '}';
    }
}
