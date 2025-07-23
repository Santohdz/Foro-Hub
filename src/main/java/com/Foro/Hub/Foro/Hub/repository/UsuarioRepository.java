package com.Foro.Hub.Foro.Hub.repository;

import com.Foro.Hub.Foro.Hub.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    
    UserDetails findByUsername(String username);
}
