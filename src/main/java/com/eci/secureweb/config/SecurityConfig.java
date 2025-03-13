package com.eci.secureweb.config;

import java.util.List;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.cors.CorsConfigurationSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // Deshabilitar CSRF
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/", "/auth/**", "/api/properties/**", "/api/properties/", 
                                          "/api/auth/login", "/api/auth/register", "/api/hello", 
                                          "/index.html", "/login.html", "/script.js", "/loginscript.js", 
                                          "/styles.css").permitAll() // Rutas públicas
                        .anyRequest().authenticated() // Todas las demás rutas requieren autenticación
                )
                .cors(cors -> cors.configurationSource(corsConfigurationSource())); // Habilitar CORS

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(List.of("https://localhost:8443")); // Permitir solicitudes desde HTTPS
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE")); // Métodos permitidos
        config.setAllowedHeaders(List.of("*")); // Permitir todos los encabezados
        config.setAllowCredentials(true); // Permitir credenciales en CORS

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config); // Aplicar CORS a todas las rutas

        return source;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // Codificador de contraseñas
    }
}