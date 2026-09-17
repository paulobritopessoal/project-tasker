package com.example.projecttasker.core.application.dtos.taskdtos;

import com.example.projecttasker.core.application.dtos.userdtos.UserMapper;
import com.example.projecttasker.core.domain.tasksmanagement.Task;

public class TaskMapper {
    public static TaskSummaryDTO toDTO(Task task) {
        return new TaskSummaryDTO(task.getId(), task.getTitle(),task.getTaskStatus(),task.getPriority(),task.getEstimateH(),task.getCompletedAt(), task.getAssignee() == null ? null : UserMapper.toDTO(task.getAssignee()));
    }
}
