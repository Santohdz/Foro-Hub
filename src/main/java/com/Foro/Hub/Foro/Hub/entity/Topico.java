package com.Foro.Hub.Foro.Hub.entity;

import com.Foro.Hub.Foro.Hub.enums.StatusTopico;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "topicos", 
       uniqueConstraints = @UniqueConstraint(columnNames = {"titulo", "mensaje"}))
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Topico {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false, length = 255)
    private String titulo;
    
    @Column(nullable = false, columnDefinition = "TEXT")
    private String mensaje;
    
    @Column(name = "fecha_creacion", nullable = false, updatable = false)
    private LocalDateTime fechaCreacion;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private StatusTopico status = StatusTopico.ABIERTO;
    
    @Column(nullable = false, length = 255)
    private String autor;
    
    @Column(nullable = false, length = 255)
    private String curso;
    
    @PrePersist
    protected void onCreate() {
        if (fechaCreacion == null) {
            fechaCreacion = LocalDateTime.now();
        }
        if (status == null) {
            status = StatusTopico.ABIERTO;
        }
    }
}
