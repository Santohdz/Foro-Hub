package com.Foro.Hub.Foro.Hub.dto;

import com.Foro.Hub.Foro.Hub.enums.StatusTopico;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class TopicoRequest {
    
    @NotBlank(message = "El título es obligatorio")
    @Size(min = 5, max = 255, message = "El título debe tener entre 5 y 255 caracteres")
    private String titulo;
    
    @NotBlank(message = "El mensaje es obligatorio")
    @Size(min = 10, message = "El mensaje debe tener al menos 10 caracteres")
    private String mensaje;
    
    @NotNull(message = "El status es obligatorio")
    private StatusTopico status;
    
    @NotBlank(message = "El autor es obligatorio")
    @Size(min = 2, max = 255, message = "El autor debe tener entre 2 y 255 caracteres")
    private String autor;
    
    @NotBlank(message = "El curso es obligatorio")
    @Size(min = 2, max = 255, message = "El curso debe tener entre 2 y 255 caracteres")
    private String curso;
}
