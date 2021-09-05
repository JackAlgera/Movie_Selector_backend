package com.jack.applications.database.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Movie {
    private Integer id;
    private String title;
    private String overview;
    @JsonProperty("release_date")
    private String releaseDate;
    private List<Genre> genres;
    @JsonProperty("poster_path")
    private String posterPath;
}
