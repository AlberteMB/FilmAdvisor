# Metabuscador de Películas

## Descripción General

Este metabuscador permite a los usuarios centralizar sus búsquedas de películas en múltiples plataformas de streaming. La aplicación mostrará en qué plataformas está disponible cada película y permitirá aplicar filtros, gestionar listas personalizadas y evitar la "paradoja de la elección" mediante sugerencias optimizadas.  

El proyecto se basa en **JustWatch** (opción gratuita) para obtener información sobre disponibilidad en plataformas de streaming y en **PostgreSQL** como base de datos. Además, se cruzarán los datos de **IMDb, Rotten Tomatoes y Filmaffinity** para filtrar películas según la puntuación del público.  

## Funcionalidades

### 1. **Configuración del Perfil del Usuario**

Los usuarios podrán configurar su perfil con las plataformas de streaming que utilizan. Esto permitirá filtrar los resultados y mostrar solo películas disponibles en los servicios que tienen contratados.  

### 2. **Búsqueda de Películas**

La aplicación contará con dos métodos de búsqueda:  

- **Búsqueda aleatoria**: Basada en filtros como año de estreno, género y plataformas disponibles. Se podrá elegir cuántas películas mostrar (3, 6 o 9).  
- **Búsqueda individual**: Permitirá buscar películas por **título, director, actor o género**.  

### 3. **Filtrado por Puntuación**

Para evitar sugerir películas de baja calidad, se permitirá filtrar según una **puntuación mínima** del público. El usuario podrá elegir entre las puntuaciones de **IMDb, Rotten Tomatoes o Filmaffinity**.  

### 4. **Gestión de Listas de Películas**

Los usuarios podrán marcar las películas según su interés, evitando que vuelvan a aparecer en futuras búsquedas:  

- **Películas vistas**: No se mostrarán de nuevo. Se podrán marcar con "me gusta" o "no me gusta".  
- **Películas descartadas**: No se mostrarán en futuras búsquedas, pero estarán accesibles en una lista de descartes.  
- **Películas aceptables**: Se guardarán para una futura decisión.  

### 5. **Botón de "Probar de Nuevo"**

Si ninguna de las películas sugeridas es interesante, el usuario podrá refrescar la lista sin marcarlas como descartadas.  

### 6. **Sinopsis y Enlaces Externos**

Al hacer clic en una película, se desplegará su sinopsis junto con enlaces a **IMDb, Filmaffinity y las plataformas de streaming donde está disponible**.  

### 7. **Actualización Periódica de la Base de Datos**

Dado que la disponibilidad de películas en plataformas de streaming cambia con el tiempo, se actualizarán periódicamente los datos almacenados en la base de datos.  

## Tecnologías y APIs

- **JustWatch API**: Para obtener información sobre disponibilidad en plataformas de streaming.  
- **IMDb API, Rotten Tomatoes API, Filmaffinity API**: Para obtener puntuaciones y detalles adicionales de las películas.  
- **PostgreSQL**: Para almacenar y gestionar la información de películas, puntuaciones y plataformas.  
- **Spring Boot (Backend)** y **React (Frontend)**: Para desarrollar la aplicación web.  

---

## **Estructura de la Base de Datos**

La base de datos utilizará **PostgreSQL** y se diseñará con las siguientes entidades:  

### **Entidades Principales**

#### **Usuario** (`users`)

Almacena la información del usuario y sus preferencias.  

| Campo      | Tipo         | Descripción                             |
| ---------- | ------------ | --------------------------------------- |
| id         | UUID (PK)    | Identificador único del usuario.        |
| email      | VARCHAR(255) | Correo electrónico del usuario (único). |
| password   | VARCHAR(255) | Contraseña encriptada.                  |
| created_at | TIMESTAMP    | Fecha de creación del usuario.          |

#### **Plataforma de Streaming** (`streaming_platforms`)

Lista de plataformas de streaming compatibles con la aplicación.  

| Campo    | Tipo         | Descripción                                   |
| -------- | ------------ | --------------------------------------------- |
| id       | UUID (PK)    | Identificador único de la plataforma.         |
| name     | VARCHAR(255) | Nombre de la plataforma (Netflix, HBO, etc.). |
| logo_url | TEXT         | URL del logo de la plataforma.                |

#### **Película** (`movies`)

Información de cada película almacenada en la base de datos.  

| Campo       | Tipo         | Descripción                            |
| ----------- | ------------ | -------------------------------------- |
| id          | UUID (PK)    | Identificador único de la película.    |
| title       | VARCHAR(255) | Título de la película.                 |
| year        | INT          | Año de estreno.                        |
| genre       | VARCHAR(100) | Género de la película.                 |
| director    | VARCHAR(255) | Director de la película.               |
| actors      | TEXT         | Lista de actores principales.          |
| synopsis    | TEXT         | Sinopsis de la película.               |
| imdb_rating | DECIMAL(3,1) | Puntuación en IMDb.                    |
| rt_rating   | INT          | Puntuación en Rotten Tomatoes.         |
| fa_rating   | DECIMAL(3,1) | Puntuación en Filmaffinity.            |
| created_at  | TIMESTAMP    | Fecha en la que se añadió la película. |

#### **Relaciones**

##### **Usuarios y Plataformas** (`user_platforms`)

Relación N:M entre usuarios y plataformas de streaming.  

| Campo       | Tipo    | Descripción                                |
| ----------- | ------- | ------------------------------------------ |
| user_id     | UUID FK | Usuario que ha seleccionado la plataforma. |
| platform_id | UUID FK | Plataforma seleccionada.                   |

##### **Películas en Plataformas** (`movie_platforms`)

Relación N:M entre películas y plataformas donde están disponibles.  

| Campo       | Tipo    | Descripción                           |
| ----------- | ------- | ------------------------------------- |
| movie_id    | UUID FK | Película disponible en la plataforma. |
| platform_id | UUID FK | Plataforma donde está disponible.     |

##### **Interacción del Usuario con las Películas** (`user_movie_interaction`)

Registro de las interacciones del usuario con las películas.  

| Campo      | Tipo                                       | Descripción                                      |
| ---------- | ------------------------------------------ | ------------------------------------------------ |
| user_id    | UUID FK                                    | Usuario que ha interactuado con la película.     |
| movie_id   | UUID FK                                    | Película en cuestión.                            |
| status     | ENUM('watched', 'discarded', 'acceptable') | Estado de la película para el usuario.           |
| liked      | BOOLEAN (NULLABLE)                         | Indica si le ha gustado (solo si ha sido vista). |
| created_at | TIMESTAMP                                  | Fecha de interacción.                            |

---

## **Flujo de Datos**

1. **Consulta de películas**:  
   
   - El frontend solicita a la API del backend una lista aleatoria de películas según los filtros del usuario.  
   - El backend obtiene datos de JustWatch y cruza la información con IMDb, Rotten Tomatoes y Filmaffinity.  
   - Se filtran las películas según la puntuación mínima establecida por el usuario.  

2. **Interacción del usuario**:  
   
   - Si el usuario marca una película como vista, se almacena en `user_movie_interaction` y no se vuelve a mostrar.  
   - Si la descarta, se guarda en la lista de descartes.  
   - Si la considera aceptable, podrá verla más tarde.  

3. **Actualización de la base de datos**:  
   
   - Se actualizará periódicamente la disponibilidad de las películas en plataformas de streaming para reflejar cambios en JustWatch.  

---

## **Mejoras Futuras**

- **Modo "Sugerencias Inteligentes"**: Analizar el historial de interacciones para recomendar películas similares.  
- **Implementación de OAuth2**: Permitir login con Google o Facebook.  
- **Versión móvil**: Optimizar la interfaz para dispositivos móviles.  

---

Este documento ahora contiene **toda la información** estructurada correctamente, incluyendo la base de datos y el flujo de datos. ¡Ahora sí, lo tienes listo para presentarlo! 🚀  
