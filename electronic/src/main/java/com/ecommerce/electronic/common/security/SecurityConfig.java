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
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth

                        // ── Public ───────────────────────────────────────────────────
                        .requestMatchers("/auth/**").permitAll()

                        // ── Products ─────────────────────────────────────────────────
                        // Anyone authenticated can read products
                        .requestMatchers(HttpMethod.GET, "/products/**").authenticated()
                        // Only ADMIN can create, update, delete products
                        .requestMatchers(HttpMethod.POST, "/products/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT,  "/products/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/products/**").hasRole("ADMIN")

                        // ── Categories ───────────────────────────────────────────────
                        .requestMatchers(HttpMethod.GET, "/categories/**").authenticated()
                        .requestMatchers(HttpMethod.POST, "/categories/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT,  "/categories/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/categories/**").hasRole("ADMIN")

                        // ── Product-Category mappings ────────────────────────────────
                        .requestMatchers(HttpMethod.GET, "/product-categories/**").authenticated()
                        .requestMatchers(HttpMethod.POST, "/product-categories/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/product-categories/**").hasRole("ADMIN")

                        // ── Cart ─────────────────────────────────────────────────────
                        // CUSTOMER and VENDOR can manage their own carts
                        .requestMatchers("/cart/**").hasAnyRole("CUSTOMER", "VENDOR", "ADMIN")

                        // ── Orders ───────────────────────────────────────────────────
                        // CUSTOMER/VENDOR can place and cancel orders; read own orders
                        .requestMatchers(HttpMethod.POST, "/orders").hasAnyRole("CUSTOMER", "VENDOR", "ADMIN")
                        .requestMatchers(HttpMethod.GET,  "/orders").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET,  "/orders/**").hasAnyRole("CUSTOMER", "VENDOR", "ADMIN")
                        .requestMatchers(HttpMethod.PUT,  "/orders/cancel/**").hasAnyRole("CUSTOMER", "VENDOR", "ADMIN")
                        // Only ADMIN can update order status (SHIPPED, DELIVERED, etc.)
                        .requestMatchers(HttpMethod.PUT,  "/orders/status/**").hasRole("ADMIN")

                        // ── Payments ─────────────────────────────────────────────────
                        .requestMatchers(HttpMethod.POST, "/payments/**").hasAnyRole("CUSTOMER", "VENDOR", "ADMIN")
                        .requestMatchers(HttpMethod.GET,  "/payments").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET,  "/payments/**").hasAnyRole("CUSTOMER", "VENDOR", "ADMIN")

                        // ── Warranties ───────────────────────────────────────────────
                        .requestMatchers(HttpMethod.GET,  "/warranties").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET,  "/warranties/**").hasAnyRole("CUSTOMER", "VENDOR", "ADMIN")
                        // Only ADMIN can manually update warranty status
                        .requestMatchers(HttpMethod.PUT,  "/warranties/**").hasRole("ADMIN")

                        // ── Service Requests ─────────────────────────────────────────
                        .requestMatchers(HttpMethod.POST, "/service-request/**").hasAnyRole("CUSTOMER", "VENDOR", "ADMIN")
                        .requestMatchers(HttpMethod.GET,  "/service-request").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET,  "/service-request/**").hasAnyRole("CUSTOMER", "VENDOR", "ADMIN")
                        // Only ADMIN can update service request status
                        .requestMatchers(HttpMethod.PUT,  "/service-request/status/**").hasRole("ADMIN")

                        // ── Catch-all ────────────────────────────────────────────────
                        .anyRequest().authenticated()
                )
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}