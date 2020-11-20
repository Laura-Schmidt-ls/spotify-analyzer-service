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
import java.util.HashMap;
import java.util.Map;

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

    private static Map<String, AuthorizationCredentials> credentialsMap = new HashMap<>();


    /**
     * Getting the link to the Spotify login page. After user logged in it redirects to the specified
     * redirect URI.
     *
     * @return link to Spotify login page.
     */
    @GetMapping("login")
    @ResponseBody
    public String spotifyLogin(@RequestParam("state") String state) {
        AuthorizationCodeUriRequest authorizationCodeUriRequest = spotifyApi.authorizationCodeUri()
            .state(state)
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
    public String getSpotifyUserCode(@RequestParam("code") String userCode, @RequestParam("state") String state) {
        AuthorizationCodeRequest authorizationCodeRequest = spotifyApi.authorizationCode(userCode)
                .build();

        try {
            final AuthorizationCodeCredentials authorizationCodeCredentials = authorizationCodeRequest.execute();

            AuthorizationCredentials authCredentials = new AuthorizationCredentials(authorizationCodeCredentials.getAccessToken(),
                    authorizationCodeCredentials.getRefreshToken(),
                    authorizationCodeCredentials.getExpiresIn().toString());
            credentialsMap.put(state, authCredentials);

        } catch (IOException | SpotifyWebApiException | org.apache.hc.core5.http.ParseException e) {
            System.out.println("Error: " + e.getMessage());
        }

        return "Please close the browser and return back to the app.";
    }

    /**
     * Returns the access-token, refresh-token and expiresIn as JSON-String.
     *
     * @param state identifier
     * @return access-token, refresh-token and expiresIn as JSON-String
     */
    @GetMapping("tokens")
    public String getTokens(@RequestParam("state") String state) {
        if(!credentialsMap.containsKey(state)) {
            return "";
        }
        AuthorizationCredentials authCredentials = credentialsMap.get(state);
        return authCredentials.toJSONString();
    }

    /**
     * Refreshes the access-token.
     *
     * @return refreshed access- and refresh-token as JSON String
     */
    @GetMapping("refresh")
    public String refreshTokens(@RequestParam("state") String state) {
        spotifyApi.setRefreshToken(credentialsMap.get(state).getRefreshToken());
        AuthorizationCodeRefreshRequest authorizationCodeRefreshRequest = spotifyApi.authorizationCodeRefresh().build();

        try {
            final AuthorizationCodeCredentials authorizationCodeCredentials = authorizationCodeRefreshRequest.execute();
            if(credentialsMap.containsKey(state)) {
                AuthorizationCredentials authCredentials = new AuthorizationCredentials(authorizationCodeCredentials.getAccessToken(),
                        authorizationCodeCredentials.getRefreshToken(),
                        authorizationCodeCredentials.getExpiresIn().toString());
                credentialsMap.put(state, authCredentials);
            }
        } catch (ParseException | SpotifyWebApiException | IOException e) {
            System.out.println("Error: " + e.getMessage());
        }

        return credentialsMap.get(state).toJSONString();
    }
}
