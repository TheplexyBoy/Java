# 🏢 CorporateTalentHub

> Proyecto formativo en **Java** que evoluciona semana a semana, desde una simple aplicación de consola con arreglos, hasta un sistema en capas (MVC + DAO) conectado a **PostgreSQL** vía Docker. Cada carpeta `semana N` es una fotografía del código en un punto distinto del aprendizaje: se conservan todas para poder comparar "antes vs. después" a medida que se incorporan nuevos conceptos de POO y features modernas de Java (8 → 21).

---

## 📖 ¿Qué es esto?

**CorporateTalentHub** simula un pequeño sistema de gestión de talento humano para una empresa: permite registrar empleados, calcular su desempeño, clasificarlos por rango salarial, generar reportes y, en su versión más avanzada, persistir todo en una base de datos real.

El objetivo **no es solo el resultado final** (la app de la `semana 5`), sino el recorrido: el repo documenta cómo un mismo dominio de negocio se va refactorizando a medida que se aprenden nuevos temas de Java y de arquitectura de software.

---

## 🗺️ Línea de tiempo del proyecto

| Semana | Foco principal | Qué se agrega |
|---|---|---|
| **1** | Fundamentos y tipos de dato | Todos los tipos primitivos, `record` (`EmpresaRecord`), comparación por referencia (`==`), manejo de `NullPointerException` |
| **2** | Estructuras de control y arreglos | Menú por consola, `switch` clásico vs. moderno, arreglos paralelos (`Empleado[]`, matriz de calificaciones), validaciones de entrada |
| **3** | Colecciones | Migración de arreglos a `ArrayList` / `HashMap`, colecciones inmutables (`List.of()`, `Map.of()`), *Sequenced Collections* de Java 21 (`getFirst()`, `getLast()`, `reversed()`), `removeIf` |
| **4** | POO avanzada | Jerarquía de clases (`Persona` → `Empleado`, `Desarrollador`, `Gerente`, `ConsultorExterno`), **sealed classes**, interfaces con métodos `default` (`Promocionable`), `record` para reportes (`DesempenioReport`) |
| **5** | Arquitectura y persistencia | Patrón **MVC + DAO**, conexión JDBC a **PostgreSQL**, manejo de excepciones de negocio propias, contenedor Docker con `docker-compose`, generación de reportes con *text blocks* |

---

## 🧱 Arquitectura (semana 5 — versión completa)

```
Vista (ConsoleView) → Controlador (EmpleadoController) → DAO (EmpleadoDAOImpl) → PostgreSQL
```

- **`view/`** – `ConsoleView`: entrada/salida por consola, no conoce reglas de negocio.
- **`controller/`** – `EmpleadoController`: orquesta el flujo (menú, validaciones básicas, manejo de excepciones) y conecta vista con datos.
- **`model/`** – entidades del dominio, interfaces (`EmpleadoDAO`, `Promocionable`) y su implementación JDBC (`EmpleadoDAOImpl`).
- **`exception/`** – jerarquía propia: `BusinessException` (checked) → `EmpleadoYaExisteException`; `DatabaseException` (unchecked) para errores de infraestructura.
- **`documentation/`** – `NotasArquitectura.java`, notas y justificaciones de las decisiones de diseño tomadas en cada iteración.

### Jerarquía de dominio

```
Persona (sealed abstract)
 ├── Empleado          → clase tradicional, con estado mutable (promedioDesempeno)
 ├── Desarrollador      implements Promocionable
 ├── Gerente             implements Promocionable
 └── ConsultorExterno
```

`Persona` es una **sealed class**: solo esas cuatro clases pueden extenderla, lo que le da control total del dominio al compilador (exhaustividad en `switch`, sin sorpresas de terceros extendiendo la jerarquía).

---

## ✨ Funcionalidades (app de consola — semana 5)

1. Registrar empleado
2. Mostrar todos los empleados
3. Actualizar empleado
4. Eliminar empleado
5. Generar reporte consolidado
0. Salir

Todas las operaciones viajan por `EmpleadoDAO` hacia PostgreSQL usando `PreparedStatement` y `try-with-resources` (cierre automático de conexiones, sin fugas de memoria).

---

## 🛠️ Tecnologías

- **Java 21** (LTS) — sealed classes, records, text blocks, pattern matching, sequenced collections
- **Maven** — gestión de dependencias y build
- **PostgreSQL 16** — persistencia (semana 5)
- **Docker / Docker Compose** — levantar la base de datos sin instalarla localmente
- **JDBC** (`org.postgresql:postgresql`) — conexión a la base de datos

---

## 📋 Requisitos previos

- Java JDK 21 o superior
- Apache Maven 3.9 o superior
- Docker y Docker Compose (solo necesario para la `semana 5`)
- Git (opcional)

Verifica las versiones instaladas:

```bash
java -version
mvn -version
docker -v
```

---

## 🚀 Cómo ejecutar cada versión

Cada semana es un módulo Maven independiente (tiene su propio `pom.xml`), así que hay que entrar a la carpeta correspondiente antes de compilar.

### Semanas 1 a 4 (sin base de datos)

```bash
cd "semana 4"          # o la semana que quieras probar
mvn clean compile
mvn exec:java -Dexec.mainClass="com.mycompany.corporatetalenthub.App"
```

> Si tu `pom.xml` no tiene el plugin `exec-maven-plugin` configurado, ejecuta la clase `App.java` directamente desde tu IDE (NetBeans, IntelliJ IDEA o Eclipse).

### Semana 5 (con PostgreSQL vía Docker)

1. Levanta la base de datos (crea el contenedor y ejecuta el script `01-schema.sql` automáticamente):

   ```bash
   cd "semana 5"
   docker compose up -d
   ```

2. Compila y ejecuta la aplicación:

   ```bash
   mvn clean compile
   mvn exec:java -Dexec.mainClass="com.riwi.talent.App"
   ```

3. Al terminar, puedes apagar el contenedor con:

   ```bash
   docker compose down
   ```

**Credenciales por defecto (solo entorno local/desarrollo):**

| Parámetro | Valor |
|---|---|
| Host | `localhost:5432` |
| Base de datos | `riwi_talent_hub` |
| Usuario | `postgres` |
| Contraseña | `postgres_123` |

> ⚠️ Estas credenciales están *hardcodeadas* en `DatabaseConnection.java` con fines didácticos. En un proyecto real deberían salir de variables de entorno o un gestor de secretos.

---

## 📂 Estructura del proyecto

```
.
├── README.md
├── semana 1   → Tipos de dato, records, comparación por referencia
├── semana 2   → Arreglos, switch, menú de consola
├── semana 3   → Colecciones (ArrayList, HashMap, inmutables, sequenced collections)
├── semana 4   → Herencia, sealed classes, interfaces default
└── semana 5   → MVC + DAO + PostgreSQL + Docker
    ├── db-init/01-schema.sql
    ├── docker-compose.yml
    ├── pom.xml
    └── src/main/java/com/riwi/talent/
        ├── App.java
        ├── controller/EmpleadoController.java
        ├── exception/ (BusinessException, DatabaseException, EmpleadoYaExisteException)
        ├── model/ (Persona, Empleado, Desarrollador, Gerente, ConsultorExterno,
        │           Promocionable, DesempenioReport, EmpresaRecord,
        │           EmpleadoDAO, EmpleadoDAOImpl, DatabaseConnection)
        └── view/ConsoleView.java
```

---

## 🎓 Conceptos de Java destacados en el código

- **Sealed classes** (`Persona permits ...`) para cerrar el dominio de tipos.
- **Records** (`EmpresaRecord`, `DesempenioReport`) para modelos inmutables sin *boilerplate*.
- **Interfaces con métodos `default`** (`Promocionable.registrarLogPromocion`) para extender contratos sin romper implementaciones existentes.
- **Text blocks** (`"""..."""`) para el menú y los reportes formateados.
- **Try-with-resources** en todo el acceso a JDBC, evitando fugas de conexiones.
- **Switch expressions** (`->`) frente al `switch` clásico con `break`.
- **Sequenced Collections** de Java 21 (`getFirst()`, `getLast()`, `reversed()`).
- **Excepciones propias**: una jerarquía *checked* (`BusinessException`) para errores de negocio esperables, y una *unchecked* (`DatabaseException`) para fallos de infraestructura.

---

## 🧪 Pruebas

```bash
mvn test
```

## 📦 Compilar el JAR

```bash
mvn package
```

El artefacto se genera en la carpeta `target/` del módulo correspondiente.

---

## 👨‍💻 Autor

- GitHub: **[Danilo-Doria](https://github.com/TheplexyBoy)**
- Mail: **andresquinteroho@gmail.com**
