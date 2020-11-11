package com.spotifyanalyzerservice.service;

/**
 * Authorization Scopes.
 *
 * @see <a href="https://developer.spotify.com/documentation/general/guides/scopes/">Spotify-Scopes</a>
 */
public interface Scopes {
    /**
     * Write access to user-provided images.
     */
    String UGC_IMAGE_UPLOAD = "ugc-image-upload";

    /**
     * Read access to a user's recently played items.
     */
    String USER_READ_RECENTLY_PLAYED = "user-read-recently-played";

    /**
     * Read access to a user's player state.
     */
    String USER_READ_PLAYBACK_STATE = "user-read-playback-state";

    /**
     * Read access to a user's top artists and tracks.
     */
    String USER_TOP_READ = "user-top-read";

    /**
     * Remote control playback of Spotify. This scope is currently available to Spotify iOS and Android SDKs.
     */
    String APP_REMOTE_CONTROL = "app-remote-control";

    /**
     * Write access to a user's public playlists.
     */
    String PLAYLIST_MODIFY_PUBLIC = "playlist-modify-public";

    /**
     * Write access to a user's playback state.
     */
    String USER_MODIFY_PLAYBACK_STATE = "user-modify-playback-state";

    /**
     * Write access to a user's private playlists.
     */
    String PLAYLIST_MODIFY_PRIVATE = "playlist-modify-private";

    /**
     * Write/delete access to the list of artists and other users that the user follows.
     */
    String USER_FOLLOW_MODIFY = "user-follow-modify";

    /**
     * Read access to a user’s currently playing content.
     */
    String USER_READ_CURRENTLY_PLAYING = "user-read-currently-playing";

    /**
     * Read access to the list of artists and other users that the user follows.
     */
    String USER_FOLLOW_READ = "user-follow-read";

    /**
     * Write/delete access to a user's "Your Music" library.
     */
    String USER_LIBRARY_MODIFY = "user-library-modify";

    /**
     * Read access to a user’s playback position in a content.
     */
    String USER_READ_PLAYBACK_POSITION = "user-read-playback-position";

    /**
     * Read access to user's private playlists.
     */
    String PLAYLIST_READ_PRIVATE = "playlist-read-private";

    /**
     * Read access to user’s email address.
     */
    String USER_READ_EMAIL = "user-read-email";

    /**
     * Read access to user’s subscription details (type of user account).
     */
    String USER_READ_PRIVATE = "user-read-private";

    /**
     * Read access to a user's "Your Music" library.
     */
    String USER_LIBRARY_READ = "user-library-read";

    /**
     * Include collaborative playlists when requesting a user's playlists.
     */
    String PLAYLIST_READ_COLLABORATIVE = "playlist-read-collaborative";

    /**
     * Control playback of a Spotify track. This scope is currently available to the Web Playback SDK.
     * The user must have a Spotify Premium account.
     */
    String STREAMING = "streaming";
}
