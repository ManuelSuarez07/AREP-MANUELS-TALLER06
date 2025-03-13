package com.eci.secureweb.controller;

import com.eci.secureweb.dto.UserDto;
import com.eci.secureweb.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserDto userDto) {
        if (userService.authenticate(userDto.getUsername(), userDto.getPassword())) {
            return ResponseEntity.status(HttpStatus.FOUND) // Redirigir al index.html
                    .header("Location", "/index.html")
                    .build();
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED) // Error de autenticación
                    .body("{\"error\": \"Usuario o contraseña incorrectos\"}");
        }
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody UserDto userDto) {
        try {
            userService.createUser(userDto.getUsername(), userDto.getPassword());
            return ResponseEntity.ok("{\"message\": \"Usuario registrado con éxito\"}");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST) // Error de validación
                    .body("{\"error\": \"" + e.getMessage() + "\"}");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR) // Error interno
                    .body("{\"error\": \"Error al registrar usuario\"}");
        }
    }
}