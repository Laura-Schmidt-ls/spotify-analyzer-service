package com.spotifyanalyzerservice.service;

import com.wrapper.spotify.SpotifyApi;
import com.wrapper.spotify.SpotifyHttpManager;
import com.wrapper.spotify.exceptions.SpotifyWebApiException;
import com.wrapper.spotify.model_objects.credentials.AuthorizationCodeCredentials;
import com.wrapper.spotify.requests.authorization.authorization_code.AuthorizationCodeRefreshRequest;
import com.wrapper.spotify.requests.authorization.authorization_code.AuthorizationCodeRequest;
import com.wrapper.spotify.requests.authorization.authorization_code.AuthorizationCodeUriRequest;
import org.apache.hc.core5.http.ParseException;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.*;

/**
 * Managing requests to Spotify-API.
 *
 * @author Laura Schmidt
 */
@RestController
@RequestMapping("api/")
public class AuthenticationController implements Const, Scopes {

    private static final URI redirectUri = SpotifyHttpManager.makeUri(REDIRECT_URL);

    private static final SpotifyApi spotifyApi = new SpotifyApi.Builder()
            .setClientId(CLIENT_ID)
            .setClientSecret(CLIENT_SECRET)
            .setRedirectUri(redirectUri)
            .build();


    /**
     * Getting the link to the Spotify login page. After user logged in it redirects to the specified
     * redirect URI.
     *
     * @return link to Spotify login page.
     */
    @GetMapping("login")
    @ResponseBody
    public String spotifyLogin() {
        AuthorizationCodeUriRequest authorizationCodeUriRequest = spotifyApi.authorizationCodeUri()
//          .state("x4xkmn9pu3j6ukrs8n")
                .scope(USER_READ_RECENTLY_PLAYED + ", "
                        + USER_TOP_READ + ", "
                        + USER_READ_CURRENTLY_PLAYING + ", "
                        + PLAYLIST_READ_PRIVATE + ", "
                        + USER_READ_PRIVATE + ", "
                        + USER_LIBRARY_READ + ", "
                        + PLAYLIST_READ_COLLABORATIVE)
                .show_dialog(true)
                .build();
        final URI uri = authorizationCodeUriRequest.execute();
        System.out.println("URI: " + uri.toString());

        return uri.toString();
    }

    /**
     * Determines the access- and refresh-token from the Spotify-API.
     *
     * @param userCode is needed to get access- and refresh-token
     * @return JSON String with access-token and refresh-token
     */
    @GetMapping(value = "user_code")
    public String getSpotifyUserCode(@RequestParam("code") String userCode) {
        AuthorizationCodeRequest authorizationCodeRequest = spotifyApi.authorizationCode(userCode)
                .build();
        System.out.println("Code: " + userCode);

        try {
            final AuthorizationCodeCredentials authorizationCodeCredentials = authorizationCodeRequest.execute();

            // Set access and refresh token for further "spotifyApi" object usage
            spotifyApi.setAccessToken(authorizationCodeCredentials.getAccessToken());
            spotifyApi.setRefreshToken(authorizationCodeCredentials.getRefreshToken());

            System.out.println("Expires in: " + authorizationCodeCredentials.getExpiresIn());
        } catch (IOException | SpotifyWebApiException | org.apache.hc.core5.http.ParseException e) {
            System.out.println("Error: " + e.getMessage());
        }

        return "{\"access-token\": \"" + spotifyApi.getAccessToken() + "\", "
                + "\"refresh-token\": \"" + spotifyApi.getRefreshToken() + "\"}";
    }

    /**
     * Refreshes the access-token.
     *
     * @return refreshed access- and refresh-token as JSON String
     */
    @GetMapping("refresh")
    public String refreshTokens() {
        AuthorizationCodeRefreshRequest authorizationCodeRefreshRequest = spotifyApi.authorizationCodeRefresh().build();

        try {
            final AuthorizationCodeCredentials authorizationCodeCredentials = authorizationCodeRefreshRequest.execute();
            spotifyApi.setAccessToken(authorizationCodeCredentials.getAccessToken());
        } catch (ParseException | SpotifyWebApiException | IOException e) {
            System.out.println("Error: " + e.getMessage());
        }

        return "{\"access-token\": \"" + spotifyApi.getAccessToken() + "\", "
                + "\"refresh-token\": \"" + spotifyApi.getRefreshToken() + "\"}";
    }

    /**
     * Getting the access- and refresh-token.
     *
     * @return access- and refresh-token as JSON String
     */
    @GetMapping("tokens")
    public String getTokens() {
        return "{\"access-token\": \"" + spotifyApi.getAccessToken() + "\", "
                + "\"refresh-token\": \"" + spotifyApi.getRefreshToken() + "\"}";
    }
}
