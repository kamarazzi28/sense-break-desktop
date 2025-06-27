package sensebreak.backendservice.auth;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.UUID;

/**
 * Service responsible for generating and validating JWT tokens.
 */
@Service
public class JwtService {

    /**
     * Token expiration time in milliseconds (24 hours).
     */
    private static final long EXPIRATION = 1000 * 60 * 60 * 24;

    /**
     * Secret key used to sign JWT tokens.
     */
    private final Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    /**
     * Generates a JWT token for a given user ID.
     *
     * @param userId the UUID of the user
     * @return a signed JWT token as a String
     */
    public String generateToken(UUID userId) {
        return Jwts.builder()
                .setSubject(userId.toString())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION))
                .signWith(key)
                .compact();
    }

    /**
     * Extracts the user ID from a given JWT token.
     *
     * @param token the JWT token
     * @return the extracted user UUID, or null if invalid
     */
    public UUID extractUserId(String token) {
        try {
            String subject = Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody()
                    .getSubject();

            return UUID.fromString(subject);
        } catch (Exception e) {
            return null;
        }
    }
}
