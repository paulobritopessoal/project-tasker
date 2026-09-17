package com.example.projecttasker.core.application.services;

import com.example.projecttasker.core.application.dtos.taskdtos.TaskSummaryDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskServiceImpl implements TaskService{
    @Override
    public TaskSummaryDTO create(TaskSummaryDTO task) {
        return null;
    }

    @Override
    public TaskSummaryDTO getById(Long id) {
        return null;
    }

    @Override
    public List<TaskSummaryDTO> getAll() {
        return List.of();
    }
}
