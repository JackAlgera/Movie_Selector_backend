package com.jack.applications.websockets.models;

public class SelectionMessageDto {

    private String userId;
    private Integer movieId;
    private String roomId;

    public SelectionMessageDto() {
    }

    public SelectionMessageDto(String userId, Integer movieId, String roomId) {
        this.userId = userId;
        this.movieId = movieId;
        this.roomId = roomId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Integer getMovieId() {
        return movieId;
    }

    public void setMovieId(Integer movieId) {
        this.movieId = movieId;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    @Override
    public String toString() {
        return "SelectionMessageDto{" +
                "userId='" + userId + '\'' +
                ", movieId='" + movieId + '\'' +
                ", roomId='" + roomId + '\'' +
                '}';
    }
}
