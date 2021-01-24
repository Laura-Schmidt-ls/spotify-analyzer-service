package com.spotifyanalyzerservice.model;

public class AuthorizationCredentials {
    String accessToken;
    String refreshToken;
    String expiresIn;

    public AuthorizationCredentials() {

    }

    public AuthorizationCredentials(String accessToken, String refreshToken, String expiresIn) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.expiresIn = expiresIn;
    }

    public String toJSONString() {
        return "{\"access-token\": \"" + this.accessToken + "\", "
                + "\"refresh-token\": \"" + this.refreshToken + "\", "
                + "\"expires-in\": \"" + this.expiresIn + "\"}";
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public String getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(String expiresIn) {
        this.expiresIn = expiresIn;
    }
}
