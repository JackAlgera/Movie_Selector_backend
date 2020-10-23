package com.jack.applications.database;

import com.jack.applications.database.daos.TmdbDAOimpl;
import com.jack.applications.utils.JsonMapper;
import com.jack.applications.database.daos.MovieDAOImpl;
import com.jack.applications.database.models.AvailableTMDbMovie;
import com.jack.applications.database.models.Movie;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Vector;
import java.util.zip.GZIPInputStream;

public class DatabaseHandler {

    private static DatabaseHandler instance;
    private JsonMapper jsonMapper = JsonMapper.getJsonMapper();

    private static TmdbDAOimpl tmdbDAOimpl;

    private DatabaseHandler() {
        this.tmdbDAOimpl = new TmdbDAOimpl();
        this.updateAllMovies();
    }

    public static DatabaseHandler getInstance() {
        if (instance == null) {
            instance = new DatabaseHandler();
        }

        return instance;
    }

    private void updateAllMovies() {
        try {
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

            AvailableTMDbMovie currentMovie = movies.get(0);

            //movies.stream().forEach(w -> System.out.println(w.toString()) );

        } catch (MalformedURLException | ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
