package com.example.projecttasker.core.application.services;

import com.example.projecttasker.core.application.dtos.userdtos.UserCreateRequestDTO;
import com.example.projecttasker.core.application.dtos.userdtos.UserResponseDTO;

import java.util.List;

public interface UserService {
    UserResponseDTO create(UserCreateRequestDTO request);
    UserResponseDTO getById(Long id);
    List<UserResponseDTO> getAll();
}
