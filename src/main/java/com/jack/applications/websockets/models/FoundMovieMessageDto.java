package com.jack.applications.websockets.models;

public class FoundMovieMessageDto {

    private String movieId;

    public FoundMovieMessageDto() {
    }

    public FoundMovieMessageDto(String movieId) {
        this.movieId = movieId;
    }

    public String getMovieId() {
        return movieId;
    }

    public void setMovieId(String movieId) {
        this.movieId = movieId;
    }
}
