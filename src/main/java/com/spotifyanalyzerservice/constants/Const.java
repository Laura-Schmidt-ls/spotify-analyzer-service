package com.spotifyanalyzerservice.constants;

public interface Const {

    String CLIENT_ID = System.getenv("SPOTIFY_CLIENT_ID");
    String CLIENT_SECRET = System.getenv("SPOTIFY_CLIENT_SECRET");

    String REDIRECT_URL = "https://spotify-analyzer-service.herokuapp.com/api/user_code/";
}
