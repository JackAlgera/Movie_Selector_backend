package com.jack.applications.database.models;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class Movie {
    private String movieId;
    private String title;
    private Long runtime;
    private MovieGenre movieGenre;
    private boolean isAdult;
}
