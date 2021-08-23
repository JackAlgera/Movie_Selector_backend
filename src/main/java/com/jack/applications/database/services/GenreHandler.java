package com.jack.applications.database.services;

import com.jack.applications.database.daos.GenreDAOImpl;
import com.jack.applications.database.models.Genre;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class GenreHandler {

    @Autowired
    private GenreDAOImpl genreDAOImpl;

    private final Map<Integer, Genre> genreMap;

    public GenreHandler() {
        this.genreMap = new HashMap<>();
    }

    @PostConstruct
    private void initialise() {
        this.genreDAOImpl.getAllGenres().forEach(genre -> genreMap.put(genre.getId(), genre));
    }

    public List<Genre> getAllGenres() {
        return new ArrayList<>(genreMap.values());
    }
}
