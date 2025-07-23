# Foro Hub - API REST

[![Java](https://img.shields.io/badge/Java-24-orange.svg)](https://www.oracle.com/java/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.5.3-brightgreen.svg)](https://spring.io/projects/spring-boot)
[![MySQL](https://img.shields.io/badge/MySQL-8.x-blue.svg)](https://www.mysql.com/)
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)

> API REST moderna para gestión de foros de discusión desarrollada con Spring Boot 3.x, JWT authentication y MySQL.

## Tabla de Contenidos

- [Descripción](#-descripción)
- [Características](#-características)
- [Tecnologías](#-tecnologías)
- [Instalación](#-instalación)
- [Configuración](#-configuración)
- [Uso](#-uso)
- [API Endpoints](#-api-endpoints)
- [Estructura del Proyecto](#-estructura-del-proyecto)
- [Licencia](#-licencia)

## Descripción

Foro Hub es una API REST completa para la gestión de foros de discusión. Permite crear, listar, actualizar y eliminar tópicos con un sistema robusto de autenticación JWT y validaciones de datos.

### Características Principales

- 🔐 **Autenticación JWT** - Sistema seguro de tokens
- 📝 **CRUD Completo** - Operaciones completas para tópicos
- 🛡️ **Validaciones Robustas** - Prevención de datos duplicados
- 🗄️ **Base de Datos MySQL** - Persistencia confiable
- 🔄 **Migraciones Flyway** - Control de versiones de BD
- 📊 **Paginación** - Listados eficientes
- 🔍 **Filtros** - Búsqueda por curso y año
- 📋 **Logging** - Trazabilidad completa

## Tecnologías

| Tecnología | Versión | Propósito |
|------------|---------|-----------|
| Java | 24 | Lenguaje principal |
| Spring Boot | 3.5.3 | Framework web |
| Spring Security | 6.x | Seguridad y autenticación |
| Spring Data JPA | 3.x | Persistencia de datos |
| MySQL | 8.x | Base de datos |
| Flyway | 9.x | Migraciones de BD |
| JWT (JJWT) | 0.12.x | Tokens de autenticación |
| Lombok | 1.18.x | Reducción de boilerplate |
| Maven | 3.x | Gestión de dependencias |

## Instalación

### Prerrequisitos

- ☕ Java 24 o superior
- 🗄️ MySQL 8.x
- 📦 Maven 3.x
- 🔧 IDE (IntelliJ IDEA recomendado)

### Pasos de Instalación

1. **Clonar el repositorio**
   ```bash
   git clone https://github.com/tu-usuario/foro-hub.git
   cd foro-hub
   ```

2. **Configurar MySQL**
   ```sql
   CREATE DATABASE foro_hub;
   ```

3. **Configurar variables de entorno (opcional)**
   ```bash
   export DB_PASSWORD=tu_password_mysql
   export JWT_SECRET=tu_clave_secreta_jwt
   ```

4. **Ejecutar la aplicación**
   ```bash
   mvn spring-boot:run
   ```

## Configuración

### Variables de Entorno (Opcionales)

El proyecto funciona con valores por defecto, pero puedes personalizar:

```bash
# Base de Datos
export DB_URL=jdbc:mysql://localhost:3306/foro_hub
export DB_USERNAME=root
export DB_PASSWORD=tu_password

# JWT
export JWT_SECRET=tu_clave_secreta_minimo_32_caracteres
export JWT_EXPIRATION=3600000

# Servidor
export SERVER_PORT=8080
```

### Configuración por Defecto

La aplicación viene preconfigurada para funcionar inmediatamente:
- **Base de datos**: `foro_hub` en localhost:3306
- **Usuario DB**: `root` / `password`
- **Puerto**: 8080
- **JWT**: Clave por defecto (cambiar en producción)

## Uso

### Credenciales por Defecto

```json
{
  "username": "admin",
  "password": "123456"
}
```

### Flujo Básico

1. **Autenticarse** en `/login`
2. **Obtener token JWT**
3. **Usar token** en header `Authorization: Bearer <token>`
4. **Realizar operaciones** CRUD en `/topicos`

## API Endpoints

### Autenticación

| Método | Endpoint | Descripción | Auth |
|--------|----------|-------------|------|
| POST | `/login` | Autenticar usuario | No |

**Ejemplo de Login:**
```bash
curl -X POST http://localhost:8080/login \
  -H "Content-Type: application/json" \
  -d '{"username":"admin","password":"123456"}'
```

### Tópicos

| Método | Endpoint | Descripción | Auth |
|--------|----------|-------------|------|
| GET | `/topicos` | Listar con paginación | Sí |
| GET | `/topicos/all` | Listar todos | Sí |
| GET | `/topicos/{id}` | Obtener por ID | Sí |
| POST | `/topicos` | Crear nuevo | Sí |
| PUT | `/topicos/{id}` | Actualizar | Sí |
| DELETE | `/topicos/{id}` | Eliminar | Sí |

**Ejemplo de Creación:**
```bash
curl -X POST http://localhost:8080/topicos \
  -H "Authorization: Bearer <token>" \
  -H "Content-Type: application/json" \
  -d '{
    "titulo": "Problema con Spring Security",
    "mensaje": "Necesito ayuda con JWT",
    "autor": "Juan Pérez",
    "curso": "Spring Boot"
  }'
```

### Modelos de Datos

#### Tópico
```json
{
  "id": 1,
  "titulo": "Título del tópico",
  "mensaje": "Contenido del mensaje",
  "fechaCreacion": "2025-01-01T10:00:00",
  "status": "ABIERTO",
  "autor": "Nombre del autor",
  "curso": "Nombre del curso"
}
```

#### Estados Disponibles
- `ABIERTO` - Tópico activo
- `CERRADO` - Tópico cerrado
- `RESUELTO` - Problema solucionado
- `EN_PROGRESO` - En proceso

## Estructura del Proyecto

```
src/main/java/com/Foro/Hub/Foro/Hub/
├── 📁 config/              # Configuraciones
│   ├── CorsConfiguration.java
│   └── SecurityConfigurations.java
├── 📁 controller/          # Controladores REST
│   ├── LoginController.java
│   └── TopicoController.java
├── 📁 dto/                 # Data Transfer Objects
│   ├── LoginRequest.java
│   ├── LoginResponse.java
│   ├── TopicoRequest.java
│   └── TopicoResponse.java
├── 📁 entity/              # Entidades JPA
│   ├── Topico.java
│   └── Usuario.java
├── 📁 enums/               # Enumeraciones
│   └── StatusTopico.java
├── 📁 exception/           # Manejo de excepciones
│   ├── DuplicateResourceException.java
│   └── GlobalExceptionHandler.java
├── 📁 repository/          # Repositorios JPA
│   ├── TopicoRepository.java
│   └── UsuarioRepository.java
├── 📁 security/            # Seguridad JWT
│   └── JwtAuthenticationFilter.java
└── 📁 service/             # Servicios de negocio
    ├── AuthenticationService.java
    ├── TokenService.java
    └── TopicoService.java
```

## Seguridad

- 🔐 **JWT Authentication** con expiración configurable
- 🛡️ **BCrypt** para hash de passwords
- ✅ **Validación** de datos de entrada
- 🚫 **Prevención** de inyección SQL con JPA
- 📝 **Logging** de eventos de seguridad
- 🔧 **Variables de entorno** para secretos

## Licencia

Este proyecto está bajo la Licencia MIT. Ver [LICENSE](LICENSE) para más detalles.

---

¡Gracias por visitar este proyecto! 🎉

Por [Santohdz](https://github.com/Santohdz) 🎴
