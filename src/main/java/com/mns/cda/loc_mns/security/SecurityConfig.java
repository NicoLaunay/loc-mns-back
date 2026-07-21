package com.mns.cda.loc_mns.security;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity // permet que la vérification des rôles se fasse au niveau des controllers
public class SecurityConfig {

    protected final PasswordEncoder passwordEncoder;
    protected final UserDetailsService userDetailsService;
    protected final JwtFilter filter;
    @Value("${cors.allowed-origins}")
    private String allowedOrigins;

    // @Bean : on remplace le comportement par défaut par notre propre authenticationProvider
    @Bean
    public AuthenticationProvider authenticationProvider() {

        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder);

        return authProvider;
    }

    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {

        return http.csrf(config -> config.disable()) // pas de csrf
                .sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // on utilise pas de cookies (connexion stateless)
                .cors(config -> config.configurationSource(corsConfigurationSource()))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/actuator/health",
                                "/login",
                                "/sign-in"
                        ).permitAll()
                        .anyRequest().authenticated()
                )
                .addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    public CorsConfigurationSource corsConfigurationSource() {
        // création d'une nouvelle config
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        // qui a accès à l'app
        corsConfiguration.setAllowedOrigins(List.of(allowedOrigins.split(",")));
        // méthodes autorisées -> "GET", "POST", "DELETE", "PUT", "PATCH"
        corsConfiguration.setAllowedMethods(List.of("GET", "POST", "DELETE", "PUT", "PATCH"));
        // en-têtes autorisés -> tous
        corsConfiguration.setAllowedHeaders(List.of("*"));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        // cette config est passée aux URLs correspondant à (/**) -> toutes
        source.registerCorsConfiguration("/**", corsConfiguration);
        return source;
    }
}
