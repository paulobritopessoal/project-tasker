package com.example.projecttasker.core.repository;

import com.example.projecttasker.core.domain.tasksmanagement.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TasksRepository extends JpaRepository<Task,Long> {

    List<Task> findByProjectId(Long projectId);
}
