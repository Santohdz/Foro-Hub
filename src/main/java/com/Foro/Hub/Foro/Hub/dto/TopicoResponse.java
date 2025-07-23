package com.Foro.Hub.Foro.Hub.dto;

import com.Foro.Hub.Foro.Hub.entity.Topico;
import com.Foro.Hub.Foro.Hub.enums.StatusTopico;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TopicoResponse {
    
    private Long id;
    private String titulo;
    private String mensaje;
    private LocalDateTime fechaCreacion;
    private StatusTopico status;
    private String autor;
    private String curso;
    
    public TopicoResponse(Topico topico) {
        this.id = topico.getId();
        this.titulo = topico.getTitulo();
        this.mensaje = topico.getMensaje();
        this.fechaCreacion = topico.getFechaCreacion();
        this.status = topico.getStatus();
        this.autor = topico.getAutor();
        this.curso = topico.getCurso();
    }
}
