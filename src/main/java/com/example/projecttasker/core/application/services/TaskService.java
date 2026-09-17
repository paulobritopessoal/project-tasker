package com.example.projecttasker.core.application.services;

import com.example.projecttasker.core.application.dtos.taskdtos.TaskSummaryDTO;

import java.util.List;

public interface TaskService {
    TaskSummaryDTO create(TaskSummaryDTO task);
    TaskSummaryDTO getById(Long id);
    List<TaskSummaryDTO> getAll();
}
