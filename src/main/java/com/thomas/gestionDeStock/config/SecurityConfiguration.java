package com.thomas.gestionDeStock.config;

import com.thomas.gestionDeStock.services.auth.ApplicationUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Arrays;
import java.util.Collections;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration{

    @Autowired
    private final ApplicationUserDetailsService applicationUserDetailsService;

    @Autowired
    private ApplicationRequestFilter applicationRequestFilter;

    public SecurityConfiguration(ApplicationUserDetailsService applicationUserDetailsService) {
        this.applicationUserDetailsService = applicationUserDetailsService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, ApplicationRequestFilter applicationRequestFilter) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session ->
                        session.sessionCreationPolicy(STATELESS)
                )
                .authorizeHttpRequests(authorize ->
                        authorize
                                .requestMatchers("/gestiondestock/v1/auth/authenticate",
                                        "/gestiondestock/v1/entreprises/create",
                                        "/gestiondestock/v1/utilisateurs/all",
                                        "/gestiondestock/v1/categories/create",
                                        "/gestiondestock/v1/categories/all",
                                        "/gestiondestock/v1/categories/**",
                                        "/gestiondestock/v1/articles/create",
                                        "/gestiondestock/v1/articles/all",
                                        "/gestiondestock/v1/articles/**",
                                        "/gestiondestock/v1/clients/create",
                                        "/gestiondestock/v1/clients/all",
                                        "/gestiondestock/v1/clients/**",
                                        "/gestiondestock/v1/fournisseurs/create",
                                        "/gestiondestock/v1/fournisseurs/all",
                                        "/gestiondestock/v1/fournisseurs/**",
                                        "http://localhost:8081/v3/api-docs.yaml",
                                        "/gestiondestock/v1/utilisateurs/find/**",
                                        "/gestiondestock/v1/utilisateurs/update/password",
                                        "/v3/api-docs.yaml",
                                        "/v2/api-docs",
                                        "/swagger-resources",
                                        "/swagger-resources/**",
                                        "/configuration/ui",
                                        "/configuration/security",
                                        "/webjars/**",
                                        "/v3/api-docs/**",
                                        "/swagger-ui/**",    // Modifié pour couvrir tout Swagger UI
                                        "/swagger-ui.html"
                                ).permitAll()
                                .anyRequest().authenticated())
                .addFilterBefore(applicationRequestFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public CorsFilter corsFilter() {
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        final CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        // Don't do this in production, use a proper list  of allowed origins
        config.setAllowedOriginPatterns(Collections.singletonList("*"));
        config.addAllowedOrigin("http://localhost:4200");
        config.setAllowedHeaders(Arrays.asList("Origin", "Content-Type", "Accept", "Authorization"));
        config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "OPTIONS", "DELETE", "PATCH"));
        source.registerCorsConfiguration("/**", config);
        // some comment here
        return new CorsFilter(source);
    }

    // Exposer un AuthenticationManager bean
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }

}
