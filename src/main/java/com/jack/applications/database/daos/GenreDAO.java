package com.jack.applications.database.daos;

import com.jack.applications.database.models.Genre;

import java.util.List;

public interface GenreDAO {

    List<Genre> getAllGenres();
}
