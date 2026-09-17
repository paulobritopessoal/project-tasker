package com.example.projecttasker.core.domain.projectmanagement;

import com.example.projecttasker.core.domain.tasksmanagement.Task;
import com.example.projecttasker.core.domain.usermanagement.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank
    @Column(unique = true, nullable = false)
    private String nomeProjeto;

    @ManyToOne
    private User user;

    @Enumerated(EnumType.STRING)
    private ProjectStatus projectStatus;

    private BigDecimal budget;

    private LocalDate createdAt;

    @OneToMany(mappedBy = "project")
    private List<Task> tasks;
}
