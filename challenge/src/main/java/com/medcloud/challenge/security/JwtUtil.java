package com.medcloud.challenge.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * Utility class for generating and validating JWT tokens.
 * This class uses the {@code "io.jsonwebtoken"} library to create and verify
 * JWT tokens.
 * The token contains information such as the user's email and role.
 * The token is signed with a secret key and has a defined expiration time.
 * 
 */
@Component
public class JwtUtil {
    // Secret key to distribute the token. It should be kept secret and not exposed.
    // It is cumbersome to use an environment variable or a secrets management
    // service to store the key.
    // However, since this is an example, the key is hardcoded.
    // The key should be long enough to ensure the security of the token.
    // The key should be kept secret and not exposed.
    private final byte[] secretKey = "22112wdawdawdawdwadwadwadwadwadwadwadawd".getBytes(); // Conversão para byte array

    // Expiration time of the token in milliseconds. The token will expire after
    // this
    // time. The expiration time should be long enough to ensure the security of the
    private final long expiration = 86400000; // One day in milliseconds

    /**
     * Generates a JWT token with the given email and role.
     * Define the subject of the token as the email of the user.
     * The role of the user is added as a claim to the token.
     * Assign the token using the HS256 algorithm and the secret key.
     * 
     * @param email the email of the user
     * @param role  the role of the user
     * @return the generated JWT token as compact string
     *         {@code .setSubject(email)}: Identifies of user
     *         {@code .claim("role", role)}: Adds the role - function of the user-
     *         for example ADMIN,
     *         USER, etc.
     *         {@code .setIssuedAt(now)}: Sets the date of issue of the token
     *         {@code .setExpiration(exp)}: Sets the expiration date of the token
     *         {@code .signWith(SignatureAlgorithm.HS256, secretKey)}: Signs the
     *         token with the
     *         secret key using the HS256 algorithm
     *         {@code .compact()}: Converts the token to a compact string
     */
    public String generateToken(String email, String role) {
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);
        Date exp = new Date(nowMillis + expiration);

        @SuppressWarnings("deprecation")
        JwtBuilder builder = Jwts.builder()
                .setSubject(email)
                .claim("role", role)
                .setIssuedAt(now)
                .setExpiration(exp)
                .signWith(SignatureAlgorithm.HS256, secretKey);
        return builder.compact();
    }

    /**
     * @param token the JWT token to be validated
     * @return subject of the token (email of the user)
     *         This method extracts the subject (email) from the JWT token.
     *         It uses the {@code "io.jsonwebtoken"} library to parse the token and
     *         extract the claims.
     *         The token is validated using the secret key.
     *         The subject of the token is the email of the user.
     *         The method returns the email of the user.
     *         {@code .setSigningKey(secretKey)}: check the assignment of the token
     *         {@code .build()}: build the parser
     *         {@code .parseClaimsJws(token)}: analyze the token
     *         {@code .getBody()}: get the body of the token the payload
     *         {@code .getSubject()}: get the subject of the token
     * 
     */
    public String extractUsername(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }

    /**
     * Extracts all claims from the given JWT token.
     * 
     * @param token the JWT token to be validated
     * @return the claims contained in the token
     */
    public Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    /**
     * Extracts the role from the given JWT token.
     * 
     * @param token the JWT token to be validated
     * @return the role contained in the token
     *         This method extracts the role from the JWT token.
     *         It uses the {@code "io.jsonwebtoken"} library to parse the token and
     *         extract the claims.
     *         The token is validated using the secret key.
     *         The role of the user is added as a claim to the token.
     *         The method returns the role of the user.
     */
    public String extractRole(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
        return claims.get("role", String.class); // Extrai a role
    }

    /**
     * Validates the given JWT token.
     * 
     * @param token the JWT token to be validated
     * @param email the email of the user
     * @return true if the token is valid, false otherwise
     *         This method validates the JWT token.
     *         It uses the {@code "io.jsonwebtoken"} library to parse the token and
     *         extract the claims.
     *         The token is validated using the secret key.
     *         The method returns true if the token is valid, false otherwise.
     *         {@code .equals(email)} : check if the email is the same as the one in
     *         the token
     *         {@code .isTokenExpired(token)}: check if the token is expired
     */
    public boolean validateToken(String token, String email) {
        String username = extractUsername(token);
        return (username.equals(email) && !isTokenExpired(token));
    }

    /**
     * Checks if the given JWT token is expired.
     * 
     * @param token the JWT token to be validated
     * @return true if the token is expired, false otherwise
     *         This method checks if the JWT token is expired.
     *         It uses the {@code "io.jsonwebtoken"} library to parse the token and
     *         extract the claims.
     *         The token is validated using the secret key.
     *         The method returns true if the token is expired, false otherwise.
     *         {@code .getExpiration()}: get the expiration date of the token
     *         {@code .before(new Date())}: check if the token is expired
     */
    private boolean isTokenExpired(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
        return claims.getExpiration().before(new Date());
    }
}