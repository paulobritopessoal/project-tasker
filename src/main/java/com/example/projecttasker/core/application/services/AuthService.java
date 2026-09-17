package com.example.projecttasker.core.application.services;


import com.example.projecttasker.core.application.dtos.authdtos.AuthResponse;
import com.example.projecttasker.core.application.dtos.authdtos.LoginRequest;
import com.example.projecttasker.core.application.dtos.authdtos.RegisterRequests;
import com.example.projecttasker.core.application.dtos.userdtos.UserCreateRequestDTO;
import com.example.projecttasker.core.application.dtos.userdtos.UserResponseDTO;
import com.example.projecttasker.core.security.JwtUtil;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service           // (1) Marca como componente de lógica de negócio
@Transactional     // (2) Todos os métodos correm dentro de uma transacção de BD
public class AuthService {

    private final UserService userService;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authManager;

    public AuthService(UserService userService, JwtUtil jwtUtil, AuthenticationManager authManager) {
        this.userService = userService;
        this.jwtUtil = jwtUtil;
        this.authManager = authManager;
    }

    public AuthResponse register(RegisterRequests request) {
        UserCreateRequestDTO createRequest = new UserCreateRequestDTO(request.nome(), request.email(), request.password());
        UserResponseDTO created = userService.create(createRequest);   // já valida email duplicado? ver nota abaixo
        String token = jwtUtil.generateToken(created.email());
        return new AuthResponse(token, created.email());
    }

    public AuthResponse login(LoginRequest request) {
        // (4) O AuthenticationManager valida username + password automaticamente
        // Se as credenciais estiverem erradas, lança BadCredentialsException
        authManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.email(), request.password())
        );

        // Se chegámos aqui, as credenciais são válidas
        String token = jwtUtil.generateToken(request.email());
        return new AuthResponse(token, request.email());
    }


    /*
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authManager;

    // Injecção pelo construtor
    public AuthService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder,
                       JwtUtil jwtUtil,
                       AuthenticationManager authManager) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
        this.authManager = authManager;
    }

    public AuthResponse register(RegisterRequests request) {
        // Verifica se o username já existe
        if (userRepository.existsByEmail(request.email())) {
            throw new RuntimeException("Email '" + request.email() + "' já está em uso");
        }

        User user = new User();
        user.setEmail(request.email());
        // (3) NUNCA guardar a password em plain text — BCrypt gera hash + salt
        user.setPassword(passwordEncoder.encode(request.password()));

        userRepository.save(user);

        // Gera e devolve o token JWT imediatamente
        String token = jwtUtil.generateToken(user.getEmail());
        return new AuthResponse(token, user.getEmail());
    }*/
}
