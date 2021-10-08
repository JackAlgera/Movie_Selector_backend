package com.jack.applications.database.models;

import com.jack.applications.webservice.models.Genre;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class GenreWrapper {
    private List<Genre> genres;
}
