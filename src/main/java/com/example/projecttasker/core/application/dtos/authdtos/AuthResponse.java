package com.example.projecttasker.core.application.dtos.authdtos;

public record AuthResponse(
    String token,     // JWT token
    String email   // Para o frontend saber quem está autenticado
) {}