package com.medcloud.challenge.security;

import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

/**
 * SecurityConfig is a configuration class that sets up security for the
 * application using Spring Security.
 * It configures CORS, CSRF protection, and the security filter chain.
 * It also defines a password encoder bean for encoding passwords.
 * 
 * @see Configuration This annotation indicates that the class is a source of
 *      spring spring beans.
 * 
 * @see EnableWebSecurity Activates Spring Security's web security support and
 *      provides the Spring MVC integration.
 * 
 * @see JwtRequestFilter
 * 
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JwtRequestFilter jwtRequestFilter;

    /**
     * Constructor for SecurityConfig.
     *
     * @param jwtRequestFilter the JwtRequestFilter to be used in the security
     *                         filter chain
     */
    public SecurityConfig(JwtRequestFilter jwtRequestFilter) {
        this.jwtRequestFilter = jwtRequestFilter; // Injeção por construtor para evitar dependência circular
    }

    /**
     * Password encoder bean for encoding passwords using BCrypt.
     *
     * @return a BCryptPasswordEncoder instance
     */
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Security filter chain bean for configuring security settings.
     * This is a core of Spring Security, where you can configure the security filter
     * @param http the HttpSecurity object to configure
     * @return a SecurityFilterChain instance
     * @throws Exception if an error occurs during configuration
     * {@code http.csrf(AbstractHttpConfigurer::disable)}: disables CSRF 
     * {cors -> cors.configurationSource(corsConfigurationSource())}: enables CORS
     * {@code authorizeHttpRequests(authorize -> authorize.requestMatchers("/login/").permitAll()
     * .requestMatchers("/user/**").permitAll()
     * .requestMatchers("/patients/**").hasRole("ADMIN").anyRequest().denyAll())}: configures authorization rules
        * {@code addFilterBefore(, UsernamePasswordAuthenticationFilter.class)}: adds the JWT filter before the default filter of authentication.Insert the  {@link JwtRequestFilter} before the 
        * {@link UsernamePasswordAuthenticationFilter} in the filter chain.This ensures that the JWT filter is executed before the default authentication filter.
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(cors -> cors.configurationSource(corsConfigurationSource())) // Enable CORS
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/login/").permitAll()
                        .requestMatchers("/user/**").permitAll()
                        .requestMatchers("/patients/**").hasRole("ADMIN")
                        .anyRequest().denyAll())
                .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    /**
     * CORS configuration bean for allowing cross-origin requests.
     *
     * @return a CorsConfigurationSource instance
     */
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("*")); // Specify your allowed origins
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS")); // Specify allowed
                                                                                                   // methods
        configuration.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type")); // Specify allowed headers
        configuration.setAllowCredentials(true); // Allow credentials
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

}
