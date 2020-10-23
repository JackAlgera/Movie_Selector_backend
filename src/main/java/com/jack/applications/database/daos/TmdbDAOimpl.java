package com.jack.applications.database.daos;

import com.jack.applications.database.models.Movie;
import com.jack.applications.utils.JsonMapper;
import org.apache.http.Header;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TmdbDAOimpl implements TmdbDAO {

    public static String imageEndpointURL = "https://image.tmdb.org/t/p/w500";
    public static String baseEndpointURL = "https://api.themoviedb.org/3/";
    public static String apiKey = "eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiIwOTY4NzNiZGZlYTRkOTdkMGFmNmI3YWI3ZmFhMzhiYiIsInN1YiI6IjVmOGRlYjRhODlmNzQ5MDAzODAzZjZjMyIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.Tl2vUrX9dV28HWN4jhwhU92h5XiDNuWScQPhbq64uJ8";

    private static String moviePath = "movie/";

    private HttpClient httpClient;

    public TmdbDAOimpl() {
        List<Header> headers = new ArrayList<>();
        headers.add(new BasicHeader("Authorization", "Bearer " + TmdbDAOimpl.apiKey));

        this.httpClient = HttpClients.custom().setDefaultHeaders(headers).build();
    }

    public HttpClient getHttpClient() {
        return httpClient;
    }

    @Override
    public Movie getMovieInfo(int movieId) {
        Movie response = null;
        HttpGet request = new HttpGet(String.format("%s%s%d", TmdbDAOimpl.baseEndpointURL, moviePath, movieId));
        try {
            response = httpClient.execute(request, httpResponse ->
                    JsonMapper.getJsonMapper().readValue(httpResponse.getEntity().getContent(), Movie.class));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }

}
