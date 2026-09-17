package com.example.projecttasker.core.application.services;

import com.example.projecttasker.core.application.dtos.projectsdtos.ProjectCreateRequestDTO;
import com.example.projecttasker.core.application.dtos.projectsdtos.ProjectMapper;
import com.example.projecttasker.core.application.dtos.projectsdtos.ProjectResponseDTO;
import com.example.projecttasker.core.domain.projectmanagement.Project;
import com.example.projecttasker.core.domain.projectmanagement.ProjectStatus;
import com.example.projecttasker.core.domain.usermanagement.User;
import com.example.projecttasker.core.repository.ProjectRepository;
import com.example.projecttasker.core.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;

@Service
public class ProjectServiceImpl implements ProjectService{

    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;

    public ProjectServiceImpl(ProjectRepository projectRepository, UserRepository userRepository) {
        this.projectRepository = projectRepository;
        this.userRepository = userRepository;
    }

    @Override
    public ProjectResponseDTO create(ProjectCreateRequestDTO project) {

        String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Utilizador autenticado não encontrado"));

        if(projectRepository.existsByNomeProjeto(project.nomeprojeto())){
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Nome Projecto '" + project.nomeprojeto() + "' já está em uso");
        }

        Project project1 = Project.builder()
                .nomeProjeto(project.nomeprojeto())
                .user(user).projectStatus(ProjectStatus.ACTIVE)
                .createdAt(LocalDate.now()).build();


        Project saved = projectRepository.save(project1);
        return ProjectMapper.toDTO(saved);
    }

    @Override
    public ProjectResponseDTO getById(Long id, User currentUser) {
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Projecto não encontrado"));

        boolean isOwner = project.getUser().getEmail().equals(currentUser.getEmail());
        if (!isOwner && !isAdmin(currentUser)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Sem permissão para aceder a este projeto");
        }
        return ProjectMapper.toDTO(project);
    }


    @Override
    public List<ProjectResponseDTO> getAll(User currentUser) {

        List<Project> projects = isAdmin(currentUser)
                ? projectRepository.findAll()
                : projectRepository.findAllByUser_Email(currentUser.getEmail());

        return projects.stream().map(ProjectMapper::toDTO).toList();
    }

    private boolean isAdmin(User user){
        return user.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
    }
}
