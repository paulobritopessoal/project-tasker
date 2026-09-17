package com.example.projecttasker.core.repository;

import com.example.projecttasker.core.domain.projectmanagement.Project;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProjectRepository extends JpaRepository<Project,Long> {
    Optional<Project> findByUserId(Long userId);
    boolean existsByNomeProjeto(String nomeProjeto);

}
