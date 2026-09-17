package com.example.projecttasker;

import com.example.projecttasker.core.application.dtos.projectsdtos.ProjectCreateRequestDTO;
import com.example.projecttasker.core.application.services.ProjectServiceImpl;
import com.example.projecttasker.core.domain.usermanagement.Roles;
import com.example.projecttasker.core.domain.usermanagement.User;
import com.example.projecttasker.core.repository.ProjectRepository;
import com.example.projecttasker.core.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProjectServiceImplTest {
    @Mock
    ProjectRepository projectRepository;
    @Mock
    UserRepository userRepository;
    @InjectMocks
    ProjectServiceImpl projectService;

    @Test
    void deveCriarProjetoParaUtilizadorAutenticado() {
        User fakeUser = User.builder()
                .id(1L)
                .nome("Paulo")
                .email("paulo@test.com")
                .role(Roles.USER)
                .build();

        try (MockedStatic<SecurityContextHolder> mocked = mockStatic(SecurityContextHolder.class)) {
            SecurityContext context = mock(SecurityContext.class);
            Authentication auth = mock(Authentication.class);
            when(auth.getName()).thenReturn("paulo@test.com");
            when(context.getAuthentication()).thenReturn(auth);
            mocked.when(SecurityContextHolder::getContext).thenReturn(context);

            when(userRepository.findByEmail("paulo@test.com")).thenReturn(Optional.of(fakeUser));
            when(projectRepository.existsByNomeProjeto("App")).thenReturn(false);
            when(projectRepository.save(any())).thenAnswer(inv -> inv.getArgument(0));

            var result = projectService.create(new ProjectCreateRequestDTO("App"));

            assertThat(result.nomeProjeto()).isEqualTo("App");
        }
    }
}
