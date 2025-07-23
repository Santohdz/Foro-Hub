package com.Foro.Hub.Foro.Hub.enums;

public enum StatusTopico {
    ABIERTO("Abierto"),
    CERRADO("Cerrado"),
    RESUELTO("Resuelto"),
    EN_PROGRESO("En Progreso");
    
    private final String descripcion;
    
    StatusTopico(String descripcion) {
        this.descripcion = descripcion;
    }
    
    public String getDescripcion() {
        return descripcion;
    }
}
