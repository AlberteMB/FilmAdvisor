# 🏗️ Arquitectura Híbrida: PostgreSQL + DynamoDB

## 📋 Resumen

Esta aplicación utiliza una arquitectura híbrida que combina lo mejor de bases de datos relacionales y NoSQL:

- **PostgreSQL**: Para datos de usuarios (relacionales, transaccionales)
- **DynamoDB**: Para datos de películas y estados de usuario (NoSQL, escalable)

## 🎯 Ventajas de esta Arquitectura

### PostgreSQL para Usuarios ✅
- **Transacciones ACID**: Perfecto para operaciones críticas como registro, login, cambio de contraseña
- **Relaciones complejas**: Si en el futuro necesitas perfiles, preferencias, listas de amigos, etc.
- **Consultas complejas**: JOINs, agregaciones, reportes de usuarios
- **Consistencia fuerte**: Datos de usuario deben ser consistentes
- **Tamaño manejable**: Los usuarios son un dataset relativamente pequeño y estable

### DynamoDB para Películas ✅
- **Escalabilidad masiva**: Puedes tener millones de películas sin problemas de rendimiento
- **Acceso por patrones**: Consultas por plataforma, género, año (perfecto para tu caso de uso)
- **Costo-efectivo**: Solo pagas por lo que usas
- **Latencia baja**: Acceso rápido a datos de películas
- **Sin mantenimiento**: AWS maneja todo el mantenimiento

### DynamoDB para UserMovieStatus ✅
- **Patrón de acceso perfecto**: USER#userId como partition key
- **Escalabilidad**: Cada usuario puede tener miles de películas vistas
- **Consultas eficientes**: Por usuario, por estado, por rating
- **Flexibilidad**: Fácil agregar nuevos campos sin migraciones

## 🗂️ Estructura de Paquetes

```
src/main/java/amb/
├── user/                    # PostgreSQL + JPA
│   ├── User.java           # @Entity con JPA
│   ├── UserRepository.java # JpaRepository
│   └── UserEndpoint.java   # Endpoints REST
├── movie/                   # DynamoDB
│   ├── Movie.java          # @DynamoDbBean
│   ├── MovieRepository.java # Interface DynamoDB
│   ├── MovieRepositoryImpl.java # Implementación DynamoDB
│   └── MovieEndpoint.java   # Endpoints REST
└── userMovieStatus/         # DynamoDB
    ├── UserMovieStatus.java # @DynamoDbBean
    ├── UserMovieStatusRepository.java # Interface DynamoDB
    ├── UserMovieStatusRepositoryImpl.java # Implementación DynamoDB
    └── UserMovieStatusEndpoint.java # Endpoints REST
```

## 🔧 Configuración

### PostgreSQL (application.properties)
```properties
# PostgreSQL - Para datos de usuarios
spring.datasource.url=jdbc:postgresql://localhost:5432/filmadvisor
spring.datasource.username=albertemb
spring.datasource.password=1234
spring.jpa.hibernate.ddl-auto=update
spring.jpa.defer-datasource-initialization=true
```

### DynamoDB (application.properties)
```properties
# DynamoDB - Para datos de películas y estados de usuario
aws.dynamodb.region=eu-central-1
# Para desarrollo local con DynamoDB Local
# aws.dynamodb.endpoint=http://localhost:8000
spring.data.dynamodb.table-name-strategy=org.springframework.data.domain.NamingStrategy#SNAKE_CASE
```

## 📊 Patrones de Acceso Optimizados

### PostgreSQL (Users)
```sql
-- Búsquedas por username/email
SELECT * FROM users WHERE username = ?
SELECT * FROM users WHERE email = ?

-- Estadísticas por rol
SELECT COUNT(*) FROM users WHERE role = 'USER'
SELECT COUNT(*) FROM users WHERE role = 'ADMIN'
```

### DynamoDB (Movies)
```java
// Query por platform (Netflix, Prime, etc.)
QueryConditional.keyEqualTo(Key.builder().partitionValue(platform).build())

// Query por platform + genre
QueryConditional.sortBeginsWith(Key.builder()
    .partitionValue(platform)
    .sortValue(genrePrefix)
    .build())

// Scan para búsquedas complejas (título, actor, director)
```

### DynamoDB (UserMovieStatus)
```java
// Query por USER#userId (todas las películas del usuario)
QueryConditional.keyEqualTo(Key.builder().partitionValue("USER#" + userId).build())

// GSI por status para estadísticas globales
QueryConditional.keyEqualTo(Key.builder().partitionValue(status.name()).build())
```

## 🚀 Casos de Uso Perfectos

### Recomendaciones
- **DynamoDB**: Consultas rápidas de películas por plataforma/género
- **PostgreSQL**: Preferencias de usuario, historial de búsquedas

### Estadísticas
- **DynamoDB**: Conteos masivos de películas vistas/rating
- **PostgreSQL**: Estadísticas de usuarios, reportes administrativos

### Búsquedas
- **DynamoDB**: Filtros complejos de películas
- **PostgreSQL**: Autenticación, gestión de usuarios

## 📈 Escalabilidad Futura

- **Usuarios**: PostgreSQL puede manejar millones de usuarios
- **Películas**: DynamoDB puede manejar billones de registros
- **Interacciones**: DynamoDB puede manejar millones de ratings/vistas por día

## 🔄 Flujo de Datos

```
Frontend (React/TypeScript)
    ↓
Vaadin Hilla Endpoints
    ↓
┌─────────────────┬─────────────────┐
│   PostgreSQL    │    DynamoDB     │
│   (Users)       │   (Movies +      │
│                 │   UserStatus)   │
└─────────────────┴─────────────────┘
```

## 🛠️ Dependencias Necesarias

### PostgreSQL
```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-jpa</artifactId>
</dependency>
<dependency>
    <groupId>org.postgresql</groupId>
    <artifactId>postgresql</artifactId>
</dependency>
```

### DynamoDB
```xml
<dependency>
    <groupId>software.amazon.awssdk</groupId>
    <artifactId>dynamodb-enhanced</artifactId>
</dependency>
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-dynamodb</artifactId>
</dependency>
```

## ✅ Beneficios de esta Arquitectura

1. **Usa cada herramienta para lo que hace mejor**
2. **Optimiza costos** (PostgreSQL para datos pequeños, DynamoDB para datos grandes)
3. **Facilita el desarrollo** (Spring Data JPA para usuarios, DynamoDB para películas)
4. **Permite escalar independientemente** cada componente
5. **Mantiene la consistencia** donde es crítica (usuarios)
6. **Maximiza el rendimiento** donde es importante (películas)

Esta arquitectura híbrida es ideal para aplicaciones que manejan tanto datos relacionales complejos como datos masivos con patrones de acceso específicos.

