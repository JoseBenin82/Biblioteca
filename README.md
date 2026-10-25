# Biblioteca - Catálogo de Libros con Gutendex API

![Java](https://img.shields.io/badge/Java-25-blue.svg)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-4.0.8-green.svg)
![Maven](https://img.shields.io/badge/Maven-3.9+-red.svg)

Aplicación de consola desarrollada en **Java 25** con **Spring Boot 4.0.8** que consume la API pública de **Gutendex** para buscar, listar y analizar estadísticas de libros del Proyecto Gutenberg.

---

## 📋 Descripción del Proyecto

Este proyecto forma parte del curso de **Alura Backend** y demuestra el consumo de APIs REST, deserialización JSON con Jackson, y el uso de Streams en Java para procesamiento de datos. La aplicación se conecta a la API de [Gutendex](https://gutendex.com/) (una API REST para el Proyecto Gutenberg) para obtener información de libros de dominio público.

### Funcionalidades Principales

1. **📚 Listado de los 10 libros más descargados** - Obtiene los libros ordenados por número de descargas de forma descendente
2. **🔍 Búsqueda de libros por título** - Permite buscar libros ingresando el nombre (búsqueda parcial, insensible a mayúsculas/minúsculas)
3. **📊 Estadísticas de descargas** - Calcula y muestra:
   - Promedio de descargas
   - Máximo de descargas
   - Mínimo de descargas

---

## 🛠️ Tecnologías Utilizadas

| Tecnología | Versión | Uso |
|------------|---------|-----|
| **Java** | 25 | Lenguaje de programación principal |
| **Spring Boot** | 4.0.8 | Framework para aplicación de consola (CommandLineRunner) |
| **Maven** | 3.9+ | Gestión de dependencias y build |
| **Jackson** | 2.18+ | Deserialización JSON a records de Java |
| **HttpClient** | JDK 11+ | Cliente HTTP nativo para consumir la API |
| **JUnit 5** | 5.10+ | Testing unitario |

---

## 📁 Estructura del Proyecto

```
Biblioteca/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/aluracursos/Biblioteca/
│   │   │       ├── BibliotecaApplication.java    # Punto de entrada Spring Boot
│   │   │       ├── model/
│   │   │       │   ├── Datos.java                # Record para respuesta paginada
│   │   │       │   ├── DatosLibros.java          # Record para datos de libro
│   │   │       │   └── DatosAutor.java           # Record para datos de autor
│   │   │       ├── principal/
│   │   │       │   └── Principal.java            # Lógica de negocio y menú
│   │   │       └── service/
│   │   │           ├── ConsumoAPI.java           # Cliente HTTP para Gutendex
│   │   │           ├── ConvierteDatos.java       # Deserialización JSON
│   │   │           └── IConvierteDatos.java      # Interfaz para conversión
│   │   └── resources/
│   │       └── application.properties            # Configuración Spring
│   └── test/
│       └── java/
│           └── com/aluracursos/Biblioteca/
│               └── BibliotecaApplicationTests.java
├── pom.xml                                       # Configuración Maven
├── mvnw / mvnw.cmd                               # Maven Wrapper
└── README.md                                     # Este archivo
```

---

## 🚀 Cómo Ejecutar el Proyecto

### Prerrequisitos

- **Java 25** o superior instalado
- **Maven 3.9+** (o usar el wrapper incluido `./mvnw`)

### Opción 1: Con Maven Wrapper (Recomendado)

```bash
# Linux / macOS
./mvnw spring-boot:run

# Windows
.\mvnw.cmd spring-boot:run
```

### Opción 2: Compilar y ejecutar JAR

```bash
# Compilar
./mvnw clean package

# Ejecutar
java -jar target/Biblioteca-0.0.1-SNAPSHOT.jar
```

### Opción 3: Desde IDE (IntelliJ IDEA / Eclipse / VS Code)

1. Importar como proyecto Maven
2. Ejecutar la clase `BibliotecaApplication.java`
3. La aplicación iniciará en la consola

---

## 💡 Uso de la Aplicación

Al ejecutar, la aplicación muestra un menú interactivo en consola:

```
Los 10 libros mas descargados:
DON QUIXOTE
PRIDE AND PREJUDICE
ALICE'S ADVENTURES IN WONDERLAND
...

Ingrese el nombre del libro que desea buscar:
> orgullo y prejuicio

Libro Encontrado
DatosLibros[titulo=Pride and Prejudice, autor=[DatosAutor[nombre=Austen, Jane, fechaDeNacimiento=1775]], idiomas=[en], numeroDeDescargas=32821]

Cantidad media de descargas: 1245.67
Cantidad maxima de descarcargas: 55231.0
Cantidad minima de descargas: 1.0
```

---

## 🔌 API Externa: Gutendex

La aplicación consume la API pública **Gutendex** (https://gutendex.com/):

- **Endpoint base**: `https://gutendex.com/books/`
- **Búsqueda**: `https://gutendex.com/books/?search={término}`
- **Respuesta**: JSON paginado con campo `results` que contiene array de libros
- **Sin autenticación requerida** - API completamente abierta

### Ejemplo de respuesta JSON

```json
{
  "count": 73952,
  "next": "https://gutendex.com/books/?page=2",
  "previous": null,
  "results": [
    {
      "id": 1342,
      "title": "Pride and Prejudice",
      "authors": [
        {
          "name": "Austen, Jane",
          "birth_year": 1775,
          "death_year": 1817
        }
      ],
      "languages": ["en"],
      "download_count": 32821
    }
  ]
}
```

---

## 🏗️ Arquitectura y Patrones

### Principios Aplicados

- **Inversión de Dependencias**: `Principal` depende de la interfaz `IConvierteDatos`, no de la implementación concreta
- **Separación de Responsabilidades**:
  - `ConsumoAPI`: Solo comunicación HTTP
  - `ConvierteDatos`: Solo deserialización JSON
  - `Principal`: Lógica de negocio y presentación
- **Records de Java**: Inmutables, concisos, ideales para DTOs
- **Streams API**: Procesamiento funcional de colecciones

### Flujo de Datos

```
┌─────────────┐     ┌─────────────┐     ┌──────────────────┐     ┌────────────┐
│  Principal  │────▶│ ConsumoAPI  │────▶│  Gutendex API    │────▶│   JSON     │
└─────────────┘     └─────────────┘     └──────────────────┘     └─────┬──────┘
                                                                        │
┌─────────────┐     ┌─────────────┐     ┌──────────────────┐           │
│  Console    │◀────│  Principal  │◀────│ ConvierteDatos   │◀──────────┘
│  Output     │     │  (Streams)  │     │ (Jackson)        │
└─────────────┘     └─────────────┘     └──────────────────┘
```

---

## 🧪 Testing

```bash
# Ejecutar tests
./mvnw test
```

Actualmente incluye test de contexto de Spring Boot básico.

---

## 📦 Build y Empaquetado

```bash
# Limpiar y compilar
./mvnw clean compile

# Ejecutar tests y empaquetar
./mvnw clean package

# Saltar tests (solo empaquetar)
./mvnw clean package -DskipTests
```

El JAR ejecutable se genera en `target/Biblioteca-0.0.1-SNAPSHOT.jar`

---

## 🤝 Contribuciones

Este es un proyecto educativo del curso **Alura Backend**. Las contribuciones son bienvenidas para:

- Mejorar la interfaz de usuario en consola
- Agregar más funcionalidades (filtros por idioma, autor, etc.)
- Implementar persistencia local (base de datos)
- Agregar tests unitarios más completos
- Mejorar manejo de errores

---

## 📄 Licencia

Proyecto educativo - Sin licencia específica. Código libre para fines de aprendizaje.

---

## 👨‍💻 Autor

Desarrollado como parte del programa **Alura Backend** - Formación en Java y Spring Boot.

---

## 📚 Recursos Adicionales

- [Documentación Spring Boot](https://spring.io/projects/spring-boot)
- [API Gutendex](https://gutendex.com/)
- [Proyecto Gutenberg](https://www.gutenberg.org/)
- [Jackson JSON Processor](https://github.com/FasterXML/jackson)
- [Java Streams API](https://docs.oracle.com/javase/8/docs/api/java/util/stream/package-summary.html)