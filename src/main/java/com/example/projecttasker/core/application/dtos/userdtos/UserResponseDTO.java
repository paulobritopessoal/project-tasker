package com.example.projecttasker.core.application.dtos.userdtos;

import com.example.projecttasker.core.domain.usermanagement.Roles;

public record UserResponseDTO(Long id,
                              String nome,
                              String email,
                              Roles role
) {}

