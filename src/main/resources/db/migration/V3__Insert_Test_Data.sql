-- Insertar usuario de prueba
INSERT INTO usuarios (username, password, enabled) VALUES 
('admin', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9P8UzwvkxHZkHvK', true);

-- Insertar algunos tópicos de prueba
INSERT INTO topicos (titulo, mensaje, fecha_creacion, status, autor, curso) VALUES 
('¿Cómo usar Spring Boot?', 'Necesito ayuda para entender los conceptos básicos de Spring Boot', NOW(), 'ABIERTO', 'usuario1', 'Spring Framework'),
('Problema con JPA', 'Tengo un error al configurar las entidades JPA', NOW(), 'ABIERTO', 'usuario2', 'Java Persistence API'),
('Configuración de seguridad', 'No logro configurar Spring Security correctamente', NOW(), 'CERRADO', 'usuario3', 'Spring Security');
