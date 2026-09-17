package com.example.projecttasker.core.application.dtos.projectsdtos;

import com.example.projecttasker.core.application.dtos.taskdtos.TaskSummaryDTO;
import com.example.projecttasker.core.application.dtos.userdtos.UserResponseDTO;
import com.example.projecttasker.core.domain.projectmanagement.Project;
import com.example.projecttasker.core.domain.projectmanagement.ProjectStatus;
import com.example.projecttasker.core.repository.ProjectRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public record ProjectResponseDTO(
        Long id,
        String nomeProjeto,
        UserResponseDTO user,
        ProjectStatus status,
        BigDecimal budget,
        LocalDate createdAt,
        List<TaskSummaryDTO> tasks
) {
}
