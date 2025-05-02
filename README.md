# uala_city_map

Resultado de la prueba técnica para el rol de **Líder Técnico Android**.

---

## Arquitectura

La aplicación sigue el patrón de **Clean Architecture**, que promueve una separación clara de responsabilidades, facilitando la mantenibilidad, escalabilidad y testabilidad del software.

### Capas principales

- **Core (Base):** Contiene elementos transversales del proyecto como constantes, extensiones y utilidades.
- **Data (Acceso a datos):** Define cómo se accede a la información desde diversas fuentes (remotas y locales).
- **Domain (Lógica de negocio):** Contiene las reglas y casos de uso propios del negocio, independientes de la infraestructura y la interfaz de usuario.
- **Presentación (UI):** Incluye las vistas, componentes, widgets y la lógica de navegación de la interfaz de usuario.

Esta estructura asegura independencia de frameworks y facilita la implementación de cambios sin impactar otras capas.

### Sistema de diseño

El **Sistema de Diseño** garantiza una experiencia de usuario consistente y unificada. Este módulo agrupa patrones visuales, componentes reutilizables, estilos y guías aplicados en todo el proyecto:

- Tipografía
- Colores
- Espaciado
- Iconografía
- Tamaños
- Widgets

#### Atomic Design

El sistema de diseño se basa en la metodología **Atomic Design**, que facilita la creación y reutilización de componentes, aportando coherencia, agilidad y escalabilidad.

- **Átomos:** Elementos fundamentales como textos, botones, inputs, íconos y animaciones.
- **Moléculas:** Composiciones de átomos que forman widgets más complejos (por ejemplo: inputs con iconos o el componente de mapa).

---

## Pruebas

### Pruebas Unitarias

Se implementaron pruebas unitarias con **JUnit5** y **Mockk** para validar la lógica de los casos de uso, viewmodels, repositorios y entidades.


### Pruebas de UI

Se utilizan pruebas automatizadas con **Espresso** para garantizar una experiencia de usuario estable y libre de errores. Estas pruebas se aplican en el módulo **designSystem**

---

## Diseño

La interfaz sigue las directrices de **Material Design**, asegurando una experiencia intuitiva y moderna.

### Diagrama de flujo de datos

```Diagrama de flujo de datos
  [UI] <--> [ViewModel] <--> [Use Case] <--> [Repository] <--> [API/Database]
```

Este diagrama ilustra el flujo de datos y la interacción entre los distintos componentes.

---

## Estrategia de Implementación

### Plataforma y dependencias

- **Plataforma:** Android
- **Lenguaje:** Kotlin
- **UI:** Jetpack Compose
- **Inyección de dependencias:** Dagger Hilt
- **Almacenamiento de datos:** Room
- **Gestión de versiones:** Version Catalog

### Calidad técnica y rendimiento

- Se aplican reglas de estilo con **Ktlint** para garantizar la calidad y consistencia del código.
- El módulo de sistema de diseño está cubierto por pruebas de instrumentación (UI).
- La cobertura mínima de pruebas unitarias es del **80%**.
- Los archivos sensibles se gestionan de forma segura mediante **GitHub Secrets**.

### Estrategia de versionamiento

Se utiliza **GitHub** con la metodología **GitFlow** y workflows automatizados:

- Solo se permiten Pull Requests (PRs) a `main` desde `develop`.
- `main` está protegida contra commits directos.
- Los PRs deben ser revisados y aprobados por al menos dos integrantes del equipo.
- Se generan automáticamente las versiones (tags) al mezclar cambios de `develop` a `main`.
- Se emplea un script que controla el formato de los commits: `[FEATURE]`, `[BUGFIX]`, `[HOTFIX]` + descripción.
- Las ramas se nombran como `feature/nombreDelDesarrollo`.

### Manejo de archivos sensibles

Los secretos se almacenan en **GitHub Secrets**, accesibles solo por el líder técnico.  
Incluyen:

- Keystore para la generación de versiones productivas.
- Configuraciones como nombre de la base de datos local y URL base de las API (ambiente productivo).

### Despliegue a producción

El artefacto se publica automáticamente en **Google Play Store**, pasando primero por la etapa Beta antes de llegar a producción.

### Instalación y configuración del proyecto

1. Colocar `google-services.json` en la carpeta `app/`.
2. Ejecutar `./gradlew installGitHooks` para instalar el hook que valida los mensajes de commits.
3. Crear un archivo `key.properties` en la raíz del proyecto con:

```key.properties
BASE_URL=https://gist.github.com/
LOCAL_DB_NAME=uala_city_dev.db
```

4. Configurar **Android Studio** para que **Ktlint** formatee automáticamente el código al guardar.

### Organización del trabajo

- **Equipo:** 2 desarrolladores Android nativos (1 para sistema de diseño, 1 para funcionalidades) + líder técnico (integración continua, despliegue y versiones).
- **Metodología:** Sprints de 2 semanas con daily meetings, refinamientos y sign-off al final del sprint.

---

## Observabilidad del éxito del producto

### Métricas clave

- **Tiempo de carga:** Monitorizado con **Firebase Performance Monitoring** (web services y base de datos local).
- **Crashes y errores:** Rastreado con **Firebase Crashlytics**.
- **Eventos de uso:** Seguimiento con **Firebase Analytics** para entender el uso de funcionalidades y comportamientos de usuario.
