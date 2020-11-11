package com.jack.applications.database.services;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.stereotype.Component;

@Component
public class ResourceService {

    private final static String POSTER_URL = "https://image.tmdb.org/t/p/w500/9Fa7tCEKIha1llGH7E41mxSpaF6.jpg";

    private static CloseableHttpClient httpClient;

    private ResourceService() {
        this.httpClient = HttpClients.createDefault();
    }

}
