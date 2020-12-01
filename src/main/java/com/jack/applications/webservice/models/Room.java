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
    private final Map<Integer, Selection> selectedMovies;

    public Room() {
        this.selectedMovies = new HashMap<>();
        this.roomId = idGenerator.getRandomId();
        this.connectedUsers = new ArrayList<>();
    }

    public User getUser(String userId) {
        for (User user : connectedUsers) {
            if (user.getUserId().equals(userId)) {
                return user;
            }
        }
        return null;
    }

    public void likeMovie(Movie selectedMovie, String userId) {
        User currentUser = null;
        for (User user : connectedUsers) {
            if (user.getUserId().equals(userId)) {
                currentUser = user;
                break;
            }
        }

        if (currentUser == null) {
            return;
        }

        if (!selectedMovies.containsKey(selectedMovie.getMovieId())) {
            Selection newSelection = new Selection(selectedMovie);
            newSelection.addToSelection(currentUser);
            selectedMovies.put(selectedMovie.getMovieId(), newSelection);

            System.out.println(String.format("Added movie with id:%s, number of users that like this movie:%s",
                    selectedMovie.getMovieId(),
                    selectedMovies.get(selectedMovie.getMovieId()).getTotalLikes()));
        } else {
            Selection existingSelection = selectedMovies.get(selectedMovie.getMovieId());
            existingSelection.addToSelection(currentUser);
            System.out.println(String.format("Movie with id:%s already exists, number of users that like this movie:%s",
                    selectedMovie.getMovieId(),
                    selectedMovies.get(selectedMovie.getMovieId()).getTotalLikes()));
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

    @Override
    public String toString() {
        return "Room{" +
                "roomId='" + roomId + '\'' +
                ", connectedUsers=" + connectedUsers +
                '}';
    }
}
