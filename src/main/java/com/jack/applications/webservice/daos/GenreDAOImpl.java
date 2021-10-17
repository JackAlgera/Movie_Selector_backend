package com.jack.applications.webservice.daos;

import com.jack.applications.webservice.tmdb.models.GenreWrapper;
import com.jack.applications.webservice.tmdb.resources.TMDBFilterKeys;
import com.jack.applications.utils.JsonMapper;
import com.jack.applications.webservice.models.Genre;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.HttpClients;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

@Service
public class GenreDAOImpl implements GenreDAO {

    @Value("${tmdb.url}")
    private String TMDB_URL;

    @Value("${tmdb.api-key}")
    private String API_KEY;

    @Autowired
    private JsonMapper jsonMapper;

    private final HttpClient httpClient;

    public GenreDAOImpl() {
        this.httpClient = HttpClients.createDefault();
    }

    @Override
    public List<Genre> getAllGenres() {
        List<Genre> response = null;

        try {
            URIBuilder builder = new URIBuilder(TMDB_URL + "genre/movie/list");
            builder.setParameter(TMDBFilterKeys.API_KEY.getTag(), API_KEY);

            HttpGet request = new HttpGet(builder.build());
            GenreWrapper wrappedResponse = httpClient.execute(request, httpResponse ->
                    jsonMapper.readValue(httpResponse.getEntity().getContent(), GenreWrapper.class));
            response = wrappedResponse.getGenres();
        } catch (URISyntaxException | IOException e) {
            e.printStackTrace();
        }

        return response;
    }
}
