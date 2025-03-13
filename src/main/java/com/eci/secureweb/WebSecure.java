package com.eci.secureweb;

import java.util.Collections;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class WebSecure {

    public static void main(String[] args) {
        // Código temporal para generar el hash de la contraseña
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String hashedPassword = passwordEncoder.encode("admin"); // Cambia "contraseña" por la contraseña que desees
        System.out.println("Hashed Password: " + hashedPassword);

        // Iniciar la aplicación Spring Boot
        SpringApplication app = new SpringApplication(WebSecure.class);
        app.setDefaultProperties(Collections.singletonMap("server.port", "5000")); 
        app.run(args);
    }
}