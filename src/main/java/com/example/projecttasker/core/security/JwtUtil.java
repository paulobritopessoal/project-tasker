package com.example.projecttasker.core.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

@Component   // (1) Regista como bean Spring — pode ser injetado com @Autowired
public class JwtUtil {

    @Value("${jwt.secret}")           // (2) Injeta o valor do application.properties
    private String secret;

    @Value("${jwt.expiration}")
    private long expiration;          // ex: 86400000 = 24 horas em ms

    // Cria a chave criptográfica a partir do segredo
    private Key getKey() {
        return Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }

    // Gera um novo token JWT para o utilizador
    public String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username)                          // quem é o utilizador
                .setIssuedAt(new Date())                       // quando foi emitido
                .setExpiration(new Date(System.currentTimeMillis() + expiration)) // quando expira
                .signWith(getKey(), SignatureAlgorithm.HS256)  // algoritmo de assinatura
                .compact();
    }

    // Extrai o username do token (sem validar expiração)
    public String extractUsername(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getKey())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    // Verifica se o token é válido (assinatura + expiração)
    public boolean isValid(String token) {
        try {
            extractUsername(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;  // token expirado, malformado, ou assinatura inválida
        }
    }
}