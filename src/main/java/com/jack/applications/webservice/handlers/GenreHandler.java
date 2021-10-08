package com.jack.applications.webservice.handlers;

import com.jack.applications.database.daos.GenreDAOImpl;
import com.jack.applications.webservice.exceptions.GenreNotFoundException;
import com.jack.applications.webservice.models.Genre;
import com.jack.applications.webservice.repos.GenreRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;

@Component
public class GenreHandler {

    @Autowired
    private GenreDAOImpl genreDAOImpl;

    @Autowired
    private GenreRepo genreRepo;

    @PostConstruct
    private void initialise() {
        updateGenres();
    }

    public Genre getGenre(Integer genreId) {
        return genreRepo.findById(genreId)
                .orElseThrow(() -> new GenreNotFoundException(genreId));
    }

    public List<Genre> getAllGenres() {
        return genreRepo.findAll();
    }

    private void updateGenres() {
        genreRepo.saveAll(genreDAOImpl.getAllGenres());
    }

    public boolean checkGenreExists(Integer genreId) {
        return genreRepo.existsById(genreId);
    }
}
