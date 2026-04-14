package com.ecommerce.electronic.common.security;

import com.ecommerce.electronic.common.filter.JwtFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JwtFilter jwtFilter;

    public SecurityConfig(JwtFilter jwtFilter) {
        this.jwtFilter = jwtFilter;
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(List.of("http://localhost:5173", "http://localhost:3000"));
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        config.setAllowedHeaders(List.of("*"));
        config.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth

                        // Public
                        .requestMatchers("/auth/**").permitAll()

                        // Products - anyone authenticated can read
                        .requestMatchers(HttpMethod.GET, "/products/**").authenticated()
                        // ADMIN or VENDOR can create/update/delete products
                        .requestMatchers(HttpMethod.POST, "/products/**").hasAnyRole("ADMIN", "VENDOR")
                        .requestMatchers(HttpMethod.PUT, "/products/**").hasAnyRole("ADMIN", "VENDOR")
                        .requestMatchers(HttpMethod.DELETE, "/products/**").hasAnyRole("ADMIN", "VENDOR")

                        // Categories - ADMIN or VENDOR manage
                        .requestMatchers(HttpMethod.GET, "/categories/**").authenticated()
                        .requestMatchers(HttpMethod.POST, "/categories/**").hasAnyRole("ADMIN", "VENDOR")
                        .requestMatchers(HttpMethod.PUT, "/categories/**").hasAnyRole("ADMIN", "VENDOR")
                        .requestMatchers(HttpMethod.DELETE, "/categories/**").hasRole("ADMIN")

                        // Product-Category mappings
                        .requestMatchers(HttpMethod.GET, "/product-categories/**").authenticated()
                        .requestMatchers(HttpMethod.POST, "/product-categories/**").hasAnyRole("ADMIN", "VENDOR")
                        .requestMatchers(HttpMethod.DELETE, "/product-categories/**").hasAnyRole("ADMIN", "VENDOR")

                        // Cart - CUSTOMER and VENDOR
                        .requestMatchers("/cart/**").hasAnyRole("CUSTOMER", "VENDOR", "ADMIN")

                        // Orders
                        .requestMatchers(HttpMethod.POST, "/orders").hasAnyRole("CUSTOMER", "VENDOR", "ADMIN")
                        .requestMatchers(HttpMethod.GET, "/orders").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/orders/**").hasAnyRole("CUSTOMER", "VENDOR", "ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/orders/cancel/**").hasAnyRole("CUSTOMER", "VENDOR", "ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/orders/status/**").hasRole("ADMIN")

                        // Payments
                        .requestMatchers(HttpMethod.POST, "/payments/**").hasAnyRole("CUSTOMER", "VENDOR", "ADMIN")
                        .requestMatchers(HttpMethod.GET, "/payments").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/payments/**").hasAnyRole("CUSTOMER", "VENDOR", "ADMIN")

                        // Warranties
                        .requestMatchers(HttpMethod.GET, "/warranties").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/warranties/**").hasAnyRole("CUSTOMER", "VENDOR", "ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/warranties/**").hasRole("ADMIN")

                        // Service Requests
                        .requestMatchers(HttpMethod.POST, "/service-request/**").hasAnyRole("CUSTOMER", "VENDOR", "ADMIN")
                        .requestMatchers(HttpMethod.GET, "/service-request").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/service-request/**").hasAnyRole("CUSTOMER", "VENDOR", "ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/service-request/status/**").hasRole("ADMIN")

                        // Admin user management
                        .requestMatchers("/admin/**").hasRole("ADMIN")

                        .anyRequest().authenticated()
                )
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}