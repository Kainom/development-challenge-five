package com.medcloud.challenge.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * This class is responsible for configuring the application
 * context,including the RestTemplate bean for making HTTP requests.
 * 
 * @see RestTemplate RestTemplate object for making HTTP requests
 *
 */

@Configuration
public class AppConfig {

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();

    }

}
