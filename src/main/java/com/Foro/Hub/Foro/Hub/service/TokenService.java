package com.Foro.Hub.Foro.Hub.service;

import com.Foro.Hub.Foro.Hub.entity.Usuario;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;

@Service
@Slf4j
public class TokenService {
    
    @Value("${api.security.token.secret}")
    private String secret;
    
    @Value("${api.security.token.expiration}")
    private Long expiration;
    
    private static final String ISSUER = "Foro Hub API";
    
    public String generateToken(Usuario usuario) {
        if (usuario == null || !StringUtils.hasText(usuario.getUsername())) {
            throw new IllegalArgumentException("Usuario no puede ser nulo y debe tener username");
        }
        
        try {
            SecretKey key = getSigningKey();
            
            return Jwts.builder()
                    .setIssuer(ISSUER)
                    .setSubject(usuario.getUsername())
                    .setIssuedAt(new Date())
                    .setExpiration(Date.from(generateExpirationDate()))
                    .signWith(key, SignatureAlgorithm.HS256)
                    .compact();
        } catch (Exception exception) {
            log.error("Error al generar token para usuario: {}", usuario.getUsername(), exception);
            throw new RuntimeException("Error al generar token", exception);
        }
    }
    
    public String getSubject(String token) {
        if (!StringUtils.hasText(token)) {
            throw new IllegalArgumentException("Token no puede ser nulo o vacío");
        }
        
        try {
            SecretKey key = getSigningKey();
            
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(key)
                    .requireIssuer(ISSUER)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
                    
            return claims.getSubject();
        } catch (JwtException exception) {
            log.warn("Token JWT inválido: {}", exception.getMessage());
            throw new RuntimeException("Token JWT inválido", exception);
        } catch (Exception exception) {
            log.error("Error inesperado al procesar token JWT", exception);
            throw new RuntimeException("Error al procesar token JWT", exception);
        }
    }
    
    public boolean isTokenValid(String token) {
        try {
            String subject = getSubject(token);
            return StringUtils.hasText(subject);
        } catch (Exception e) {
            return false;
        }
    }
    
    private SecretKey getSigningKey() {
        if (!StringUtils.hasText(secret)) {
            throw new IllegalStateException("JWT secret no está configurado");
        }
        return Keys.hmacShaKeyFor(secret.getBytes());
    }
    
    private Instant generateExpirationDate() {
        return LocalDateTime.now()
                .plusSeconds(expiration / 1000)
                .toInstant(ZoneOffset.of("-03:00"));
    }
}
