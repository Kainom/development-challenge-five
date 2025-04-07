package com.medcloud.challenge.security;

import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;

/**
 * JwtRequestFilter is a filter that intercepts incoming HTTP requests to check
 * for
 * the presence of a JWT token in the Authorization header. If a valid token is
 * found, it extracts the user's email and role from the token and sets the
 * authentication in the SecurityContext.
 * 
 * @see JwtUtil
 * @see OncePerRequestFilter abstract class that ensures that the filter
 *      executes
 *      once per request.Avoids problems if the filter is triggered multiple
 *      times in the same filter chain.
 *     @{code JwtUtil} utility class for generating ,validating JWT tokens and extract claims as email and role
 */
@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;


    /**
     * This method is called for each incoming request. It checks for the presence
     * of a JWT token in the Authorization header and sets the authentication in the
     * SecurityContext if the token is valid.
     * 
     * @param request  the incoming HTTP request.Contains the data of request,headers,body etc 
     * @param response the HTTP response.Used to send responses (e.g. 403 error if token is invalid).
     * @param chain    the filter chain.Represents the remaining filter chain in Spring Security. Calling chain.doFilter() passes through the future request.
     * @throws ServletException if an error occurs during filtering
     * @throws IOException      if an I/O error occurs
     * {@code request.getHeader("Authorization")}: get the Authorization header
     * {@code authorizationHeader.startsWith("Bearer ")}: check if the header contains the correct pattern 
     * {@code jwt = authorizationHeader.substring(7)}: remove the "Bearer " prefix
     * {@code jwtUtil.extractAllClaims(jwt)}: extract all claims from the token
     * {@code claims.getSubject()}: get the subject (email) from the claims
     * {@code claims.get("role", String.class)}: get the role from the claims
     * {@code new SimpleGrantedAuthority("ROLE_" + role)}: create a new authority
     * {@code new UsernamePasswordAuthenticationToken(email, null, Collections.singletonList(authority))}: create a new
     * UsernamePasswordAuthenticationToken with the email and authority
     * {@code SecurityContextHolder.getContext().setAuthentication(authentication)}: set the authentication in the
     * SecurityContext
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        final String authorizationHeader = request.getHeader("Authorization");

        String email = null;
        String jwt = null;

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            jwt = authorizationHeader.substring(7); // Remove "Bearer "
            try {
                Claims claims = jwtUtil.extractAllClaims(jwt);
                email = claims.getSubject();
                String role = claims.get("role", String.class); // Obtém a role

                // Configura as authorities com base na role extraída
                SimpleGrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + role);

                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(email,
                        null, Collections.singletonList(authority));

                SecurityContextHolder.getContext().setAuthentication(authentication);
            } catch (Exception e) {
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                return;
            }
        }
        chain.doFilter(request, response);
    }
}
