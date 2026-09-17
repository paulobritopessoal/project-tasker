package com.example.projecttasker.core.application.dtos.userdtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserCreateRequestDTO(@NotBlank String nome,
                                   @Email @NotBlank String email,
                                   @NotBlank @Size(min = 8) String password
) {}
