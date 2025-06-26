package sensebreak.gui;

import java.util.UUID;

public class AuthSession {
    private static String token;
    private static String username;
    public static UUID userId;

    public static void setToken(String t) { token = t; }
    public static String getToken() { return token; }

    public static void setUsername(String u) { username = u; }
    public static String getUsername() { return username; }

    public static void setUserId(UUID id) {userId = id;}
    public static UUID getUserId() {return userId;}
}
