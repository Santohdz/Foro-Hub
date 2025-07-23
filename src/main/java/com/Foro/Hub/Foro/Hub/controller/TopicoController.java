package com.Foro.Hub.Foro.Hub.controller;

import com.Foro.Hub.Foro.Hub.dto.TopicoRequest;
import com.Foro.Hub.Foro.Hub.dto.TopicoResponse;
import com.Foro.Hub.Foro.Hub.service.TopicoService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/topicos")
@RequiredArgsConstructor
@Slf4j
public class TopicoController {
    
    private final TopicoService topicoService;
    
    @PostMapping
    @Transactional
    public ResponseEntity<TopicoResponse> registrarTopico(@Valid @RequestBody TopicoRequest request) {
        log.info("Registrando nuevo tópico: {}", request.getTitulo());
        
        TopicoResponse response = topicoService.registrarTopico(request);
        
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(response);
    }
    
    @GetMapping
    public ResponseEntity<Page<TopicoResponse>> listarTopicos(
            @RequestParam(required = false) String curso,
            @RequestParam(required = false) Integer anio,
            @PageableDefault(size = 10, sort = "fechaCreacion") Pageable pageable) {
        
        log.debug("Listando tópicos - Curso: {}, Año: {}, Página: {}", curso, anio, pageable.getPageNumber());
        
        Page<TopicoResponse> response = topicoService.listarTopicos(curso, anio, pageable);
        
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/all")
    public ResponseEntity<List<TopicoResponse>> listarTodosLosTopicos() {
        log.debug("Listando todos los tópicos sin paginación");
        
        List<TopicoResponse> response = topicoService.listarTodosLosTopicos();
        
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<TopicoResponse> obtenerTopicoPorId(@PathVariable Long id) {
        log.debug("Obteniendo tópico por ID: {}", id);
        
        TopicoResponse response = topicoService.obtenerTopicoPorId(id);
        
        return ResponseEntity.ok(response);
    }
    
    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<TopicoResponse> actualizarTopico(
            @PathVariable Long id, 
            @Valid @RequestBody TopicoRequest request) {
        
        log.info("Actualizando tópico con ID: {}", id);
        
        TopicoResponse response = topicoService.actualizarTopico(id, request);
        
        return ResponseEntity.ok(response);
    }
    
    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<Void> eliminarTopico(@PathVariable Long id) {
        log.info("Eliminando tópico con ID: {}", id);
        
        topicoService.eliminarTopico(id);
        
        return ResponseEntity.noContent().build();
    }
}
