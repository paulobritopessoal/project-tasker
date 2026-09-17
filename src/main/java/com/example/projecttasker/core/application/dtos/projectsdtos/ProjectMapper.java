package com.example.projecttasker.core.application.dtos.projectsdtos;

import com.example.projecttasker.core.application.dtos.taskdtos.TaskMapper;
import com.example.projecttasker.core.application.dtos.taskdtos.TaskSummaryDTO;
import com.example.projecttasker.core.application.dtos.userdtos.UserMapper;
import com.example.projecttasker.core.domain.projectmanagement.Project;

import java.util.stream.Collectors;

public class ProjectMapper {
    public static ProjectResponseDTO toDTO(Project project){
        return new ProjectResponseDTO(project.getId(),project.getNomeProjeto(), UserMapper.toDTO(project.getUser()),project.getProjectStatus(),project.getBudget(),project.getCreatedAt(), project.getTasks().stream().map(TaskMapper::toDTO).collect(Collectors.toList()));
    }
}
