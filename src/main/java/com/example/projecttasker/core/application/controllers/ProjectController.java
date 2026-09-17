package com.example.projecttasker.core.application.controllers;


import com.example.projecttasker.core.application.dtos.projectsdtos.ProjectCreateRequestDTO;
import com.example.projecttasker.core.application.dtos.projectsdtos.ProjectResponseDTO;
import com.example.projecttasker.core.application.services.ProjectService;
import com.example.projecttasker.core.domain.usermanagement.User;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/projects")
public class ProjectController {
    private final ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @PostMapping
    public ResponseEntity<ProjectResponseDTO> create(@Valid @RequestBody ProjectCreateRequestDTO request) {
        ProjectResponseDTO created = projectService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProjectResponseDTO> getById(@PathVariable Long id, @AuthenticationPrincipal User currentUser) {
        return ResponseEntity.ok(projectService.getById(id, currentUser));
    }

    @GetMapping
    public ResponseEntity<List<ProjectResponseDTO>> getAll() {
        return ResponseEntity.ok(projectService.getAll());
    }
}
