package com.example.projecttasker.core.security;

import com.example.projecttasker.core.application.services.UserServiceImpl;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {   // (1) Garante execução única por request

    private final JwtUtil jwtUtil;
    private final UserServiceImpl userDetailsService;

    // (2) Injecção pelo construtor — melhor prática vs @Autowired no campo
    public JwtAuthFilter(JwtUtil jwtUtil, UserServiceImpl userDetailsService) {
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        // 1. Lê o header Authorization
        String authHeader = request.getHeader("Authorization");

        // 2. Se não houver token, passa para o próximo filtro (o Spring Security
        //    bloqueará se a rota precisar de autenticação)
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        // 3. Extrai o token (remove "Bearer ")
        String token = authHeader.substring(7);
        String username = null;
        try {
            username = jwtUtil.extractUsername(token);
        } catch (io.jsonwebtoken.JwtException | IllegalArgumentException e) {
            // Token malformado, expirado ou assinatura inválida — continua sem autenticar
            filterChain.doFilter(request, response);
            return;
        }

        // 4. Se temos username e ainda não há autenticação no contexto actual
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            UserDetails userDetails = userDetailsService.loadUserByUsername(username);

            if (jwtUtil.isValid(token)) {
                // 5. Cria o objecto de autenticação
                UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(
                                userDetails,
                                null,                            // credenciais (null porque já validámos o token)
                                userDetails.getAuthorities()     // roles/permissões
                        );
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                // 6. Regista no SecurityContext — a partir daqui, o Spring sabe quem está autenticado
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }

        // 7. Passa para o próximo filtro da cadeia
        filterChain.doFilter(request, response);
    }
}
