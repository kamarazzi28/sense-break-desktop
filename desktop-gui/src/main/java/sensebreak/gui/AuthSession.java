package sensebreak.gui;

public class AuthSession {
    private static String token;
    private static String username;

    public static void setToken(String t) { token = t; }
    public static String getToken() { return token; }

    public static void setUsername(String u) { username = u; }
    public static String getUsername() { return username; }
}
