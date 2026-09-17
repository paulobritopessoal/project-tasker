package com.example.projecttasker.core.application.dtos.projectsdtos;

import jakarta.validation.constraints.NotBlank;

public record ProjectCreateRequestDTO(@NotBlank String nomeprojeto){
}
