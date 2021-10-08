package com.jack.applications.webservice.repos;

import com.jack.applications.webservice.models.Genre;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GenreRepo extends JpaRepository<Genre, Integer> {
}
