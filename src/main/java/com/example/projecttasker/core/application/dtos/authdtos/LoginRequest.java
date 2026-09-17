package com.example.projecttasker.core.application.dtos.authdtos;

public record LoginRequest(
        String email,
        String password
) {}
