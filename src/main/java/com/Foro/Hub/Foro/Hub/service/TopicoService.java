package com.Foro.Hub.Foro.Hub.service;

import com.Foro.Hub.Foro.Hub.dto.TopicoRequest;
import com.Foro.Hub.Foro.Hub.dto.TopicoResponse;
import com.Foro.Hub.Foro.Hub.entity.Topico;
import com.Foro.Hub.Foro.Hub.exception.DuplicateResourceException;
import com.Foro.Hub.Foro.Hub.repository.TopicoRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class TopicoService {
    
    private final TopicoRepository topicoRepository;
    
    @Transactional
    public TopicoResponse registrarTopico(TopicoRequest request) {
        log.info("Registrando nuevo tópico: {}", request.getTitulo());
        
        if (topicoRepository.existsByTituloAndMensaje(request.getTitulo(), request.getMensaje())) {
            log.warn("Intento de crear tópico duplicado: {} - {}", request.getTitulo(), request.getMensaje());
            throw new DuplicateResourceException("Ya existe un tópico con el mismo título y mensaje");
        }
        
        Topico topico = new Topico();
        topico.setTitulo(request.getTitulo());
        topico.setMensaje(request.getMensaje());
        topico.setStatus(request.getStatus());
        topico.setAutor(request.getAutor());
        topico.setCurso(request.getCurso());
        topico.setFechaCreacion(LocalDateTime.now());
        
        Topico topicoGuardado = topicoRepository.save(topico);
        log.info("Tópico creado exitosamente con ID: {}", topicoGuardado.getId());
        
        return new TopicoResponse(topicoGuardado);
    }
    
    public Page<TopicoResponse> listarTopicos(String curso, Integer anio, Pageable pageable) {
        log.debug("Listando tópicos - Curso: {}, Año: {}, Página: {}", curso, anio, pageable.getPageNumber());
        
        Page<Topico> topicos = topicoRepository.findByCursoAndAnio(curso, anio, pageable);
        return topicos.map(TopicoResponse::new);
    }
    
    public List<TopicoResponse> listarTodosLosTopicos() {
        log.debug("Listando todos los tópicos sin paginación");
        
        List<Topico> topicos = topicoRepository.findAll();
        return topicos.stream()
                .map(TopicoResponse::new)
                .toList();
    }
    
    public TopicoResponse obtenerTopicoPorId(Long id) {
        log.debug("Obteniendo tópico por ID: {}", id);
        
        Topico topico = topicoRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Tópico no encontrado con ID: {}", id);
                    return new EntityNotFoundException("Tópico no encontrado con ID: " + id);
                });
        
        return new TopicoResponse(topico);
    }
    
    @Transactional
    public TopicoResponse actualizarTopico(Long id, TopicoRequest request) {
        log.info("Actualizando tópico con ID: {}", id);
        
        Topico topico = topicoRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Intento de actualizar tópico inexistente con ID: {}", id);
                    return new EntityNotFoundException("Tópico no encontrado con ID: " + id);
                });
        
        if (!topico.getTitulo().equals(request.getTitulo()) || 
            !topico.getMensaje().equals(request.getMensaje())) {
            if (topicoRepository.existsByTituloAndMensaje(request.getTitulo(), request.getMensaje())) {
                log.warn("Intento de actualizar a contenido duplicado: {} - {}", request.getTitulo(), request.getMensaje());
                throw new DuplicateResourceException("Ya existe un tópico con el mismo título y mensaje");
            }
        }
        
        topico.setTitulo(request.getTitulo());
        topico.setMensaje(request.getMensaje());
        topico.setStatus(request.getStatus());
        topico.setAutor(request.getAutor());
        topico.setCurso(request.getCurso());
        
        Topico topicoActualizado = topicoRepository.save(topico);
        log.info("Tópico actualizado exitosamente con ID: {}", id);
        
        return new TopicoResponse(topicoActualizado);
    }
    
    @Transactional
    public void eliminarTopico(Long id) {
        log.info("Eliminando tópico con ID: {}", id);
        
        if (!topicoRepository.existsById(id)) {
            log.warn("Intento de eliminar tópico inexistente con ID: {}", id);
            throw new EntityNotFoundException("Tópico no encontrado con ID: " + id);
        }
        
        topicoRepository.deleteById(id);
        log.info("Tópico eliminado exitosamente con ID: {}", id);
    }
}
