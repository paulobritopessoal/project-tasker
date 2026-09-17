package com.example.projecttasker;

import com.example.projecttasker.core.application.dtos.userdtos.UserCreateRequestDTO;
import com.example.projecttasker.core.application.dtos.userdtos.UserResponseDTO;
import com.example.projecttasker.core.application.services.UserServiceImpl;
import com.example.projecttasker.core.domain.usermanagement.Roles;
import com.example.projecttasker.core.domain.usermanagement.User;
import com.example.projecttasker.core.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {
    @Mock
    UserRepository userRepository;
    @Mock
    PasswordEncoder passwordEncoder;
    @InjectMocks
    UserServiceImpl userService;

    @Test
    void deveCriarUserComPasswordEncriptada() {
        var request = new UserCreateRequestDTO("Paulo", "paulo@test.com", "password123");
        when(userRepository.existsByEmail("paulo@test.com")).thenReturn(false);
        when(passwordEncoder.encode("password123")).thenReturn("hash-fake");
        when(userRepository.save(any(User.class))).thenAnswer(inv -> inv.getArgument(0));

        UserResponseDTO result = userService.create(request);

        assertThat(result.email()).isEqualTo("paulo@test.com");
        verify(passwordEncoder).encode("password123");
        ArgumentCaptor<User> captor = ArgumentCaptor.forClass(User.class);
        verify(userRepository).save(captor.capture());
        assertThat(captor.getValue().getPassword()).isEqualTo("hash-fake"); // nunca a password em claro
        assertThat(captor.getValue().getRole()).isEqualTo(Roles.USER);
    }
}
