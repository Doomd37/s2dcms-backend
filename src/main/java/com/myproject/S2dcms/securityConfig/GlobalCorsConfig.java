package com.myproject.S2dcms.securityConfig;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.List;

@Configuration
public class GlobalCorsConfig {

    @Bean
    public CorsFilter corsFilter() {
        CorsConfiguration config = new CorsConfiguration();

        // Allow only your frontend origin
        config.setAllowedOrigins(List.of("http://localhost:5173"));

        // Allowed HTTP methods
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));

        // Allow all headers (so JWT Authorization header works)
        config.setAllowedHeaders(List.of("*"));

        // Do NOT set allowCredentials(true) since you are not using cookies
        // config.setAllowCredentials(false); // default is false

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();

        // Apply this CORS policy to all endpoints
        source.registerCorsConfiguration("/**", config);

        return new CorsFilter(source);
    }
}