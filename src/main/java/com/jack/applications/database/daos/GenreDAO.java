package com.jack.applications.database.daos;

import com.jack.applications.webservice.models.Genre;

import java.util.List;

public interface GenreDAO {

    List<Genre> getAllGenres();
}
