package com.example.projecttasker.core.application.dtos.taskdtos;

import com.example.projecttasker.core.application.dtos.userdtos.UserResponseDTO;
import com.example.projecttasker.core.domain.tasksmanagement.TaskStatus;

import java.time.LocalDate;

public record TaskSummaryDTO(
        Long id,
        String title,
        TaskStatus status,
        int priority,
        double estimateH,
        LocalDate completedAt,
        UserResponseDTO userResponseDTO
) {}
