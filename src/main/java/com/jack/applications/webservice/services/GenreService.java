package com.jack.applications.webservice.services;

import com.jack.applications.webservice.daos.GenreDAO;
import com.jack.applications.webservice.exceptions.GenreNotFoundException;
import com.jack.applications.webservice.models.Genre;
import com.jack.applications.webservice.repos.GenreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;

@Service
public class GenreService {

    @Autowired
    private GenreDAO genreDAO;

    @Autowired
    private GenreRepository genreRepository;

    @PostConstruct
    private void initialise() {
        updateGenres();
    }

    public Genre getGenre(Integer genreId) {
        return genreRepository.findById(genreId)
                .orElseThrow(() -> new GenreNotFoundException(genreId));
    }

    public List<Genre> getAllGenres() {
        return genreRepository.findAll();
    }

    private void updateGenres() {
        List<Genre> test = genreDAO.getAllGenres();
        genreRepository.saveAll(genreDAO.getAllGenres());
    }
}
