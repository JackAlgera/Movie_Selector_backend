package com.jack.applications.database;

import com.jack.applications.database.daos.TmdbDAOimpl;
import com.jack.applications.database.models.*;
import com.jack.applications.utils.JsonMapper;
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

    private TmdbDAOimpl tmdbDAOimpl;

    private Map<String, Movie> allMovies;

    public DatabaseHandler() {
        this.allMovies = new HashMap<>();
        this.tmdbDAOimpl = new TmdbDAOimpl();
    }

    public void updateAllMovies() {
        try {
            Map<String, Language> languages = new HashMap<>();
//            languageRepository.findAll().forEach(language -> {
//                languages.put(language.getLanguageCode(), language);
//            });
            Map<String, Country> countries = new HashMap<>();

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

            for (int i = 0; i < 20; i++) {
                AvailableTMDbMovie currentMovie = movies.get(i);
                Movie movie = tmdbDAOimpl.getMovieInfo(currentMovie.getMovieId());

                allMovies.put(movie.getImdbId(), movie);
            }

            //movies.stream().forEach(w -> System.out.println(w.toString()) );

        } catch (MalformedURLException | ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Return all movies from DB
     * @return
     */
    public List<Movie> getAllMovies() {
        return new ArrayList<>(allMovies.values());
    }

    public Movie getMovie(String movieId) {
        return allMovies.get(movieId);
    }

}
