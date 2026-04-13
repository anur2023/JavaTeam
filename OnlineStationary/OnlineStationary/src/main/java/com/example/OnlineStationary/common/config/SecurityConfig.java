package com.example.OnlineStationary.common.config;

import com.example.OnlineStationary.common.security.JwtAuthFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    private final JwtAuthFilter jwtAuthFilter;

    public SecurityConfig(JwtAuthFilter jwtAuthFilter) {
        this.jwtAuthFilter = jwtAuthFilter;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth

                        // ── Auth: public ──────────────────────────────────────────
                        .requestMatchers("/auth/register", "/auth/login").permitAll()

                        // ── Products/Categories/Brands: public read ───────────────
                        .requestMatchers(
                                org.springframework.http.HttpMethod.GET,
                                "/products/**", "/categories/**", "/brands/**"
                        ).permitAll()

                        // ── Admin-only write operations ───────────────────────────
                        .requestMatchers(
                                org.springframework.http.HttpMethod.POST,
                                "/products/**", "/categories/**", "/brands/**"
                        ).hasRole("ADMIN")
                        .requestMatchers(
                                org.springframework.http.HttpMethod.PUT,
                                "/products/**", "/categories/**", "/brands/**"
                        ).hasRole("ADMIN")
                        .requestMatchers(
                                org.springframework.http.HttpMethod.DELETE,
                                "/products/**", "/categories/**", "/brands/**"
                        ).hasRole("ADMIN")

                        // ── Everything else requires login ────────────────────────
                        .anyRequest().authenticated()
                )
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}