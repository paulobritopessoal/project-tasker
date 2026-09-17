package com.example.projecttasker.core.application.dtos.userdtos;

import com.example.projecttasker.core.domain.usermanagement.User;

public class UserMapper {
    public static UserResponseDTO toDTO(User user){
        return new UserResponseDTO(user.getId(),user.getNome(),user.getEmail(),user.getRole());
    }
}
