package com.jack.applications.database.daos;

import com.jack.applications.database.models.Genre;
import com.jack.applications.database.models.GenreWrapper;
import com.jack.applications.database.resources.TMDBConstants;
import com.jack.applications.database.resources.TMDBFilterKeys;
import com.jack.applications.utils.JsonMapper;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.HttpClients;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

@Component
public class GenreDAOImpl implements GenreDAO {

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
            URIBuilder builder = new URIBuilder(TMDBConstants.TMDB_ENDPOINT + "genre/movie/list");
            builder.setParameter(TMDBFilterKeys.API_KEY.getTag(), TMDBConstants.API_KEY);

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
