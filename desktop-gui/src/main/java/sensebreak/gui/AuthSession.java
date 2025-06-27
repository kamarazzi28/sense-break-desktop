package sensebreak.gui;

import java.util.UUID;

/**
 * Static utility class for storing authentication session data (token, username, user ID)
 * during the lifetime of the JavaFX application.
 */
public class AuthSession {

    private static String token;
    private static String username;
    public static UUID userId;

    /**
     * Sets the authentication token.
     *
     * @param t the JWT token string
     */
    public static void setToken(String t) {
        token = t;
    }

    /**
     * Gets the authentication token.
     *
     * @return the JWT token string
     */
    public static String getToken() {
        return token;
    }

    /**
     * Sets the username of the authenticated user.
     *
     * @param u the username
     */
    public static void setUsername(String u) {
        username = u;
    }

    /**
     * Gets the username of the authenticated user.
     *
     * @return the username
     */
    public static String getUsername() {
        return username;
    }

    /**
     * Sets the UUID of the authenticated user.
     *
     * @param id the user's UUID
     */
    public static void setUserId(UUID id) {
        userId = id;
    }

    /**
     * Gets the UUID of the authenticated user.
     *
     * @return the user's UUID
     */
    public static UUID getUserId() {
        return userId;
    }
}
