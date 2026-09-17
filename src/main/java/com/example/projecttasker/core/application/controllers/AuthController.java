package com.example.projecttasker.core.application.controllers;


import com.example.projecttasker.core.application.dtos.authdtos.AuthResponse;
import com.example.projecttasker.core.application.dtos.authdtos.LoginRequest;
import com.example.projecttasker.core.application.dtos.authdtos.RegisterRequests;
import com.example.projecttasker.core.application.services.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController                                      // (1) @Controller + @ResponseBody
@RequestMapping("/api/auth")                         // (2) Prefixo base para todas as rotas
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(
            @Valid @RequestBody RegisterRequests request) {   // (4) Valida o DTO antes de entrar
        AuthResponse response = authService.register(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);  // (5) 201 Created
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid
            @RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.login(request));  // 200 OK
    }
}