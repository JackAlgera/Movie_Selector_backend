package com.jack.applications.webservice.models;

import com.jack.applications.database.models.Movie;
import com.jack.applications.utils.IdGenerator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Room {

    private final static IdGenerator idGenerator = IdGenerator.getIdGenerator();

    private String roomId;
    private ArrayList<User> connectedUsers;
    private final Map<String, Selection> selectedMovies;
    private Selection movieFound;

    public Room() {
        this.selectedMovies = new HashMap<>();
        this.roomId = idGenerator.getRandomId();
        this.connectedUsers = new ArrayList<>();
        this.movieFound = null;
    }

    public User getUser(String userId) {
        for (User user : connectedUsers) {
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

        User currentUser = null;
        for (User user : connectedUsers) {
            if (user.getUserId().equals(userId)) {
                currentUser = user;
                break;
            }
        }

        if (currentUser == null) {
            return false;
        }

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
        this.connectedUsers.add(newUser);
    }

    public void removeUser(String userId) {
        this.connectedUsers.remove(userId);
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public ArrayList<User> getConnectedUsers() {
        return connectedUsers;
    }

    public void setConnectedUsers(ArrayList<User> connectedUsers) {
        this.connectedUsers = connectedUsers;
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
