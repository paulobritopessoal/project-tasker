package com.example.projecttasker.core.domain.tasksmanagement;

import com.example.projecttasker.core.domain.projectmanagement.Project;
import com.example.projecttasker.core.domain.usermanagement.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDate;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter

@Entity
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank
    private String title;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id")
    private Project project;
    @ManyToOne
    private User assignee;

    @Enumerated(EnumType.STRING)
    private TaskStatus taskStatus;

    private int priority;
    private double estimateH;
    private LocalDate createdAt;
    private LocalDate completedAt;

}
