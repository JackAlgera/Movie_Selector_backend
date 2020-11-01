package com.jack.applications.database;

import com.jack.applications.database.daos.TmdbDAOimpl;
import com.jack.applications.database.models.*;
import com.jack.applications.database.repositories.CountryRepository;
import com.jack.applications.database.repositories.GenreRepository;
import com.jack.applications.database.repositories.LanguageRepository;
import com.jack.applications.database.repositories.MovieRepository;
import com.jack.applications.utils.JsonMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.GZIPInputStream;

@Component
public class DatabaseHandler {

    private JsonMapper jsonMapper = JsonMapper.getJsonMapper();

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private GenreRepository genreRepository;
    @Autowired
    private CountryRepository countryRepository;
    @Autowired
    private LanguageRepository languageRepository;

    private TmdbDAOimpl tmdbDAOimpl;

    public DatabaseHandler() {
        this.tmdbDAOimpl = new TmdbDAOimpl();
    }

    public void updateAllMovies() {
        try {
            Map<String, Language> languages = new HashMap<>();
            languageRepository.findAll().forEach(language -> {
                languages.put(language.getLanguageCode(), language);
            });
            Map<String, Country> countries = new HashMap<>();
            countryRepository.findAll().forEach(country -> {
                countries.put(country.getCountryCode(), country);
            });

            ArrayList<AvailableTMDbMovie> movies = new ArrayList<>();

            URL url = new URL("http://files.tmdb.org/p/exports/movie_ids_10_23_2020.json.gz");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            BufferedReader in = new BufferedReader(new InputStreamReader(new GZIPInputStream(connection.getInputStream())));
            String inputLine;
            StringBuffer content = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                AvailableTMDbMovie currentMovie = jsonMapper.readValue(inputLine, AvailableTMDbMovie.class);
                if (!currentMovie.isAdult() && !currentMovie.isVideo()) {
                    movies.add(currentMovie);
                }
            }

            in.close();
            connection.disconnect();
            System.out.println(movies.size());

            AvailableTMDbMovie currentMovie = movies.get(10);
            Movie movie = tmdbDAOimpl.getMovieInfo(currentMovie.getMovieId());
            System.out.println(movie.toString());

            checkMovieDetails(movie, languages, countries);

            //movies.stream().forEach(w -> System.out.println(w.toString()) );

        } catch (MalformedURLException | ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void checkMovieDetails(Movie movie, Map<String, Language> languages, Map<String, Country> countries) {
        if (movieRepository.existsById(movie.getMovieId())) {
            return;
        }

//        movie.getGenres().stream().forEach(genre -> {
//            if (!genreRepository.existsById(genre.getGenreId())) {
//                genreRepository.save(genre);
//            }
//        });

//        movie.getSpokenLanguages().forEach(language -> {
//            if (!languages.containsKey(language.getLanguageCode())) {
//                languages.put(language.getLanguageCode(), language);
//                languageRepository.save(language);
//            }
//        });
//
//        movie.getProductionCountries().forEach(country -> {
//            if (!countries.containsKey(country.getCountryCode())) {
//                countries.put(country.getCountryCode(), country);
//                countryRepository.save(country);
//            }
//        });
        System.out.println("HERE :" + movie.toString());

        movieRepository.save(movie);
    }

    /**
     * Return all movies from DB
     * @return
     */
    public List<Movie> getAllMovies() {
        return movieRepository.findAll();
    }

}
