package com.spotifyanalyzerservice.service;

import com.wrapper.spotify.SpotifyHttpManager;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@SpringBootApplication
@RestController
public class AuthentificationMain {
    private static final String CLIENT_ID = "0389937726e547e49bcf1302d5a5e758";
    private static final String CLIENT_SECRET = "083e8e8b0c73498b9705f1f5be826df9";
    private static final String REDIRECT_URL_LOCAL = "https://localhost:8080/redirect";
    private static final String REDIRECT_URL_HEROKU = "https://spotify-analyzer-service.herokuapp.com/redirect";

    public static void main(String[] args) throws IOException {
        SpringApplication.run(AuthentificationMain.class, args);
        callSpotifyService();
    }

    @GetMapping(path = "/auth")
    public static String callSpotifyService() {
        return SpotifyService.authenticate(CLIENT_ID, REDIRECT_URL_HEROKU);
    }
}
