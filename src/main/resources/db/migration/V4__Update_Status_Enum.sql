-- Actualizar la tabla topicos para usar enum de status
ALTER TABLE topicos MODIFY COLUMN status VARCHAR(20) NOT NULL;

-- Actualizar registros existentes para usar valores del enum
UPDATE topicos SET status = 'ABIERTO' WHERE status = 'abierto' OR status = 'Abierto';
UPDATE topicos SET status = 'CERRADO' WHERE status = 'cerrado' OR status = 'Cerrado';
UPDATE topicos SET status = 'RESUELTO' WHERE status = 'resuelto' OR status = 'Resuelto';

-- Agregar constraint para validar valores del enum
ALTER TABLE topicos ADD CONSTRAINT chk_status 
CHECK (status IN ('ABIERTO', 'CERRADO', 'RESUELTO', 'EN_PROGRESO'));
