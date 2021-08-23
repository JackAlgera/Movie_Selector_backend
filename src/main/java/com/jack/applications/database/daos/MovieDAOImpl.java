package com.jack.applications.database.daos;

import com.jack.applications.database.models.Movie;
import com.jack.applications.database.models.TMDBRequestWrapper;
import com.jack.applications.database.resources.TMDBConstants;
import com.jack.applications.database.resources.TMDBFilter;
import com.jack.applications.database.resources.TMDBFilterKeys;
import com.jack.applications.utils.IdGenerator;
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
public class MovieDAOImpl implements MovieDAO {

    @Autowired
    private JsonMapper jsonMapper;

    @Autowired
    public IdGenerator idGenerator;

    private final HttpClient httpClient;

    public MovieDAOImpl() {
        this.httpClient = HttpClients.createDefault();
    }

    public List<Movie> getMovies(List<TMDBFilter> filters) {
        List<Movie> response = null;

        try {
            URIBuilder builder = new URIBuilder(TMDBConstants.TMDB_ENDPOINT + "discover/movie");
            builder.setParameter(TMDBFilterKeys.API_KEY.getTag(), TMDBConstants.API_KEY);
            filters.forEach(filter -> {
                TMDBFilterKeys key = TMDBFilterKeys.getValue(filter.getKey());
                if (key != null) {
                    builder.setParameter(key.getTag(), filter.getVal());
                } else {
                    System.out.printf("Wrong filter key value : %s%n", filter);
                }
            });

            HttpGet request = new HttpGet(builder.build());
            TMDBRequestWrapper wrappedResponse = httpClient.execute(request, httpResponse ->
                    jsonMapper.readValue(httpResponse.getEntity().getContent(), TMDBRequestWrapper.class));
            response = wrappedResponse.getResults();
        } catch (URISyntaxException | IOException e) {
            e.printStackTrace();
        }

        return response;
    }

    @Override
    public Movie getMovie(Integer movieId) {
        Movie response = null;

        try {
            URIBuilder builder = new URIBuilder(TMDBConstants.TMDB_ENDPOINT + "movie/" + movieId);
            builder.setParameter(TMDBFilterKeys.API_KEY.getTag(), TMDBConstants.API_KEY);

            HttpGet request = new HttpGet(builder.build());
            response = httpClient.execute(request, httpResponse -> {
                System.out.println(httpResponse.getEntity().getContent());
                return jsonMapper.readValue(httpResponse.getEntity().getContent(), Movie.class);
            });
        } catch (URISyntaxException | IOException e) {
            e.printStackTrace();
        }

        return response;
    }
}
