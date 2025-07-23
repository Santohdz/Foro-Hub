package com.Foro.Hub.Foro.Hub.repository;

import com.Foro.Hub.Foro.Hub.entity.Topico;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TopicoRepository extends JpaRepository<Topico, Long> {
    
    boolean existsByTituloAndMensaje(String titulo, String mensaje);
    
    @Query("SELECT t FROM Topico t WHERE " +
           "(:curso IS NULL OR t.curso = :curso) AND " +
           "(:anio IS NULL OR YEAR(t.fechaCreacion) = :anio)")
    Page<Topico> findByCursoAndAnio(@Param("curso") String curso, 
                                   @Param("anio") Integer anio, 
                                   Pageable pageable);
}
