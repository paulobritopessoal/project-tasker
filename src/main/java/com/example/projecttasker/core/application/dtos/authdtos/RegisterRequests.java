package com.example.projecttasker.core.application.dtos.authdtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RegisterRequests(
        @NotBlank(message = "Nome é obrigatório") String nome,
        @NotBlank(message = "Email é obrigatório") @Email String email,
        @NotBlank(message = "Password é obrigatória") @Size(min = 8) String password
) {}