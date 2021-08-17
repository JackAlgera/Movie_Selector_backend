package com.jack.applications.webservice.models;

import com.jack.applications.database.models.Movie;
import com.jack.applications.utils.IdGenerator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Room {
    private final static Long MAX_TIME_BEFORE_CLOSING_ROOM = 120L * 1000L; // 120s

    private String roomId;
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

        if (!selectedMovies.containsKey(selectedMovie.getMovieId())) {
            Selection newSelection = new Selection(selectedMovie);
            newSelection.addToSelection(currentUser);
            selectedMovies.put(selectedMovie.getMovieId(), newSelection);

            System.out.printf("Added movie with id:%s, number of users that like this movie:%s%n",
                    selectedMovie.getMovieId(),
                    selectedMovies.get(selectedMovie.getMovieId()).getTotalLikes());
            return false;
        } else {
            Selection existingSelection = selectedMovies.get(selectedMovie.getMovieId());
            existingSelection.addToSelection(currentUser);
            System.out.printf("Movie with id:%s already exists, number of users that like this movie:%s%n",
                    selectedMovie.getMovieId(),
                    selectedMovies.get(selectedMovie.getMovieId()).getTotalLikes());
            if (existingSelection.getTotalLikes() > 1 && existingSelection.getTotalLikes() >= numberOfUsersConnected) {
                movieFound = existingSelection;
                timeBeforeClosingRoom = System.currentTimeMillis();
                return true;
            } else {
                return false;
            }
        }
    }

    public void addUser(User newUser) {
        System.out.println(String.format("Adding user %s to room %s", newUser.getUserName(), this.roomId));
        this.connectedUsers.put(newUser.getUserId(), newUser);
    }

    public void removeUser(String userId) {
        System.out.println("Removing user: " + userId);
        User removedUser = this.connectedUsers.remove(userId);
        System.out.println("Removed user: " + removedUser.getUserName() + "from room " + this.getRoomId());
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

    public boolean shouldCloseRoom() {
        if (timeBeforeClosingRoom < 0) {
            return false;
        }

        return (System.currentTimeMillis() - timeBeforeClosingRoom) > MAX_TIME_BEFORE_CLOSING_ROOM;
    }
}
