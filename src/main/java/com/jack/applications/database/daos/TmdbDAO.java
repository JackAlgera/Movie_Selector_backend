package com.jack.applications.database.daos;

import com.jack.applications.database.models.Movie;

public interface TmdbDAO {

    Movie getMovieInfo(int movieId);

}
