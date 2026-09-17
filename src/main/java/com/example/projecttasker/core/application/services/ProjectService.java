package com.example.projecttasker.core.application.services;

import com.example.projecttasker.core.application.dtos.projectsdtos.ProjectCreateRequestDTO;
import com.example.projecttasker.core.application.dtos.projectsdtos.ProjectResponseDTO;

import java.util.List;


public interface ProjectService {
    ProjectResponseDTO create(ProjectCreateRequestDTO project);
    ProjectResponseDTO getById(Long id);
    List<ProjectResponseDTO> getAll();
}
