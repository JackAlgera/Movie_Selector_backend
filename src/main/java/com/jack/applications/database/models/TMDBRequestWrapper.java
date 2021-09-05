package com.jack.applications.database.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class TMDBRequestWrapper {
    private Integer page;
    private Integer totalPages;
    private Integer totalResults;
    private List<MovieWithoutGenres> results;
}
