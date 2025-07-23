package com.Foro.Hub.Foro.Hub.controller;

import com.Foro.Hub.Foro.Hub.dto.LoginRequest;
import com.Foro.Hub.Foro.Hub.dto.LoginResponse;
import com.Foro.Hub.Foro.Hub.entity.Usuario;
import com.Foro.Hub.Foro.Hub.service.TokenService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
@RequiredArgsConstructor
@Slf4j
public class LoginController {
    
    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;
    
    @PostMapping
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest loginRequest) {
        try {
            UsernamePasswordAuthenticationToken authToken = 
                new UsernamePasswordAuthenticationToken(
                    loginRequest.username(), 
                    loginRequest.password()
                );
            
            Authentication authentication = authenticationManager.authenticate(authToken);
            String token = tokenService.generateToken((Usuario) authentication.getPrincipal());
            
            log.info("Usuario autenticado exitosamente: {}", loginRequest.username());
            return ResponseEntity.ok(new LoginResponse(token));
            
        } catch (BadCredentialsException e) {
            log.warn("Intento de login fallido para usuario: {}", loginRequest.username());
            return ResponseEntity.badRequest().build();
        } catch (AuthenticationException e) {
            log.error("Error de autenticación para usuario: {}", loginRequest.username(), e);
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            log.error("Error inesperado durante el login para usuario: {}", loginRequest.username(), e);
            return ResponseEntity.internalServerError().build();
        }
    }
}
