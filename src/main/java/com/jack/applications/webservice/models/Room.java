package com.jack.applications.webservice.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.*;

@Data
@Entity
public class Room implements Serializable {
    @JsonIgnore
    private final static Long MAX_TIME_BEFORE_CLOSING_ROOM = 600_000L; // 600s = 10 mins

    @Id
    @Column(updatable = false, nullable = false, length = 20)
    private String roomId;
    @OneToMany(mappedBy = "room", cascade = CascadeType.DETACH)
    @JsonManagedReference
    private List<User> connectedUsers;
    @OneToMany(mappedBy = "roomId")
    private List<Selection> userRatings;
    private Integer foundMovieId;
    private Long timeBeforeClosingRoom;

    public Room() {
        this.foundMovieId = null;
        this.timeBeforeClosingRoom = System.currentTimeMillis();
    }

    public Room(String roomId) {
        this.roomId = roomId;
        this.timeBeforeClosingRoom = System.currentTimeMillis();
    }

//    public boolean likeMovie(Integer movieId, String userId, Integer likeRating) {
//        timeBeforeClosingRoom = System.currentTimeMillis();
//
//        if (movieFound != null) {
//            return true;
//        }
//
//        if (!connectedUsers.containsKey(userId)) {
//            // TODO : might need to throw 404 error here
//            return false;
//        }
//
//        Selection currentSelection;
//
//        if (!selectedMovies.containsKey(movieId)) {
//            currentSelection = new Selection(movieId);
//            selectedMovies.put(movieId, currentSelection);
//            System.out.printf("Added movie with id:%s", movieId);
//        } else {
//            currentSelection = selectedMovies.get(movieId);
//            System.out.printf("Movie with id:%s already exists. ", movieId);
//        }
//
//        currentSelection.addToSelection(userId, likeRating);
//        System.out.printf("Number of users that like this movie:%s%n", selectedMovies.get(movieId).getTotalRatings());
//
//        return foundMovie(currentSelection);
//    }

//    public boolean foundMovie(Selection selection) {
//        if (selection.getTotalRatings() > 1 &&
//                selection.getTotalRatings() >= getTotalConnectedUsers() &&
//                selection.allRatingsAboveValue(4)) {
//            movieFound = selection;
//            timeBeforeClosingRoom = System.currentTimeMillis();
//            return true;
//        } else {
//            return false;
//        }
//    }

//    public void addUser(User user) {
//        System.out.printf("Adding user %s to room %s%n", user.getUserName(), this.roomId);
//        connectedUsers.a (user.getUserId(), user);
//    }
//
//    public User removeUser(UUID userId) {
//        return connectedUsers.remove(userId);
//    }

//    public int getTotalConnectedUsers() {
//        return connectedUsers.values().size();
//    }
//
//    public List<Selection> getSelectedMovies() {
//        return new ArrayList<>(selectedMovies.values());
//    }

//    public List<Movie> getUnratedMoviesForUser(String userId, Integer maxMoviesPerRequest) {
//        if (maxMoviesPerRequest < 0) {
//            throw new IncorrectRequestException("maxMoviesPerRequest needs to be greater that 0.");
//        }
//
//        if (!connectedUsers.containsKey(userId)) {
//            throw new NotFoundException(String.format("User with Id %s not in room %s", userId, roomId));
//        }
//
//        return selectedMovies.values().stream()
//                .filter(selection -> !selection.userLikedMovie(userId))
//                .limit(maxMoviesPerRequest)
//                .map(Selection::getMovie)
//                .collect(Collectors.toList());
//    }

//    public boolean checkIfUserRatedMovie(String userId, Integer movieId) {
//        if (!selectedMovies.containsKey(movieId)) {
//            return false;
//        }
//
//        return selectedMovies.get(movieId).userLikedMovie(userId);
//    }

//    public Selection getMovieFound() {
//        return movieFound;
//    }

    public Long getTimeBeforeClosingRoom() {
        return System.currentTimeMillis() - timeBeforeClosingRoom;
    }

    public boolean shouldCloseRoom() {
        return this.getTimeBeforeClosingRoom() > MAX_TIME_BEFORE_CLOSING_ROOM;
    }

    @PreRemove
    private void preRemove() {
        for (User user : connectedUsers) {
            user.setRoom(null);
        }
        userRatings.clear();
    }
}
