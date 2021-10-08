package com.jack.applications.webservice.repos;

import com.jack.applications.webservice.models.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieRepo extends JpaRepository<Movie, Integer> {
}
