package com.spotifyanalyzerservice.service;

import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

@Service
public class SpotifyService {

    public SpotifyService() {

    }

    public static String authenticate(final String CLIENT_ID, final String REDIRECT_URL) {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setAccept(Collections.singletonList(MediaType.ALL));
        httpHeaders.setContentType(new MediaType("application", "json"));

        String url = "https://accounts.spotify.com/authorize?client_id=" + CLIENT_ID
                + "&response_type=code&redirect_uri=" + REDIRECT_URL
                + "&scope=user-read-private%20user-read-email";

        HttpEntity<String> entity = new HttpEntity<>("", httpHeaders);

        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);

        return response.getBody();
    }
}
