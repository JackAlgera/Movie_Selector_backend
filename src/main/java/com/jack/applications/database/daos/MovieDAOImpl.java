package com.jack.applications.database.daos;

import com.jack.applications.database.models.MovieWithoutGenres;
import com.jack.applications.database.models.TMDBRequestWrapper;
import com.jack.applications.database.resources.TMDBConstants;
import com.jack.applications.database.resources.TMDBFilter;
import com.jack.applications.database.resources.TMDBFilterKeys;
import com.jack.applications.utils.IdGenerator;
import com.jack.applications.utils.JsonMapper;
import com.jack.applications.webservice.handlers.GenreHandler;
import com.jack.applications.webservice.models.Genre;
import com.jack.applications.webservice.models.Movie;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.HttpClients;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class MovieDAOImpl implements MovieDAO {

    @Autowired
    private JsonMapper jsonMapper;

    @Autowired
    private IdGenerator idGenerator;

    @Autowired
    public GenreHandler genreHandler;

    private final HttpClient httpClient;

    public MovieDAOImpl() {
        this.httpClient = HttpClients.createDefault();
    }

    public List<Movie> getMovies(List<TMDBFilter> filters, Integer page) {
        List<Movie> response = null;

        try {
            URIBuilder builder = new URIBuilder(TMDBConstants.TMDB_ENDPOINT + "discover/movie");
            builder.setParameter(TMDBFilterKeys.API_KEY.getTag(), TMDBConstants.API_KEY);
            builder.setParameter(TMDBFilterKeys.PAGE.getTag(), page.toString());
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
            response = convertMovie(wrappedResponse.getResults());
        } catch (URISyntaxException | IOException e) {
            e.printStackTrace();
        }

        return response;
    }

    @Override
    public Optional<Movie> getMovie(Integer movieId) {
        Optional<Movie> response = Optional.empty();

        try {
            URIBuilder builder = new URIBuilder(TMDBConstants.TMDB_ENDPOINT + "movie/" + movieId);
            builder.setParameter(TMDBFilterKeys.API_KEY.getTag(), TMDBConstants.API_KEY);

            HttpGet request = new HttpGet(builder.build());
            response = Optional.of(httpClient.execute(request, httpResponse ->
                jsonMapper.readValue(httpResponse.getEntity().getContent(), Movie.class)));
        } catch (URISyntaxException | IOException e) {
            e.printStackTrace();
        }

        return response;
    }

    private List<Movie> convertMovie(List<MovieWithoutGenres> moviesWithoutGenres) {
        return moviesWithoutGenres.stream()
                .map(movie -> Movie.builder()
                    .id(movie.getId())
                    .title(movie.getTitle())
                    .overview(movie.getOverview())
                    .releaseDate(movie.getReleaseDate())
                    .posterPath(movie.getPosterPath())
                    .genres(movie.getGenreIds().stream()
                            .filter(genreHandler::checkGenreExists)
                            .map(genreId -> new Genre(genreId, genreHandler.getGenre(genreId).getName()))
                            .collect(Collectors.toList()))
                    .build())
                .collect(Collectors.toList());
    }
}
