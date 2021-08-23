package com.jack.applications.database.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Movie {
    private Integer id;
    private String title;
    private String overview;
    private String releaseDate;
    private List<Genre> genres;
    private String homepage;
}
