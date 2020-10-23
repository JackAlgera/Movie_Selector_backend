package com.jack.applications.web.database;

import com.jack.applications.utils.JsonMapper;
import com.jack.applications.web.database.models.AvailableTMDbMovie;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.Vector;
import java.util.zip.GZIPInputStream;

public class DatabaseHandler {

    private static DatabaseHandler instance;
    private JsonMapper jsonMapper = JsonMapper.getJsonMapper();
    private Vector<AvailableTMDbMovie> movies;

    private DatabaseHandler() {
        this.movies = new Vector<>();
        this.getAllMovieIds();
    }

    public static DatabaseHandler getInstance() {
        if (instance == null) {
            instance = new DatabaseHandler();
        }

        return instance;
    }

    private void getAllMovieIds() {

        URL url = null;
        try {
            url = new URL("http://files.tmdb.org/p/exports/movie_ids_10_23_2020.json.gz");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            int status = connection.getResponseCode();
            BufferedReader in = new BufferedReader(new InputStreamReader(new GZIPInputStream(connection.getInputStream())));
            String inputLine;
            StringBuffer content = new StringBuffer();

            float min = 999;
            float max = -999;

            while ((inputLine = in.readLine()) != null) {
                AvailableTMDbMovie currentMovie = jsonMapper.readValue(inputLine, AvailableTMDbMovie.class);
                if (!currentMovie.isAdult() && !currentMovie.isVideo()) {
                    movies.add(currentMovie);
                    if (currentMovie.getPopularity() < min) {
                        min = currentMovie.getPopularity();
                    }
                    if (currentMovie.getPopularity() > max) {
                        max = currentMovie.getPopularity();
                    }
                }
            }

            in.close();
            connection.disconnect();
            System.out.println(movies.size() + " - min:" + min + " - max:" + max);

            //AvailableTMDbMovie currentMovie = movies.get(0);

            //movies.stream().forEach(w -> System.out.println(w.toString()) );

        } catch (MalformedURLException | ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
