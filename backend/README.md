# 🍔 Algo que Pedir – Backend (Kotlin + Spring Boot)

API REST desarrollada con **Kotlin + Spring Boot** que implementa la lógica de negocio del sistema de pedidos de comida.

Esta aplicación provee los endpoints necesarios para la gestión de usuarios, pedidos, locales e ingredientes, y es consumida por dos clientes frontend (React y Svelte).

---

## 🚀 Funcionalidades

- API RESTful para gestión de pedidos y usuarios.
- Separación en capas: Controller · Service · Repository · Model.
- Reglas de negocio implementadas en el dominio.
- Manejo global de excepciones.
- Uso de DTOs para desacoplar la API del modelo interno.
- Tests unitarios e integración.
- CI configurado con GitHub Actions.

---

## 🏗 Arquitectura

El proyecto sigue una arquitectura en capas típica de Spring Boot:
Controller → Service → Repository → Model

- **Controller**: expone endpoints HTTP.
- **Service**: contiene lógica de negocio y validaciones.
- **Repository**: almacenamiento en memoria (sin base de datos persistente).
- **Model**: entidades del dominio y aplicación de patrones de diseño.

La información se almacena en memoria durante la ejecución.

---

## 🛠 Stack Tecnológico

<p align="center">
  <img src="https://img.shields.io/badge/Kotlin-7F52FF?style=for-the-badge&logo=kotlin&logoColor=white" />
  <img src="https://img.shields.io/badge/Spring_Boot-6DB33F?style=for-the-badge&logo=springboot&logoColor=white" />
  <img src="https://img.shields.io/badge/Gradle-02303A?style=for-the-badge&logo=gradle&logoColor=white" />
  <img src="https://img.shields.io/badge/JUnit-25A162?style=for-the-badge&logo=junit5&logoColor=white" />
</p>

---

## 🧪 Testing

El proyecto incluye:

- Tests unitarios
- Tests de integración
- Validación de reglas de negocio
- Ejecución automatizada en CI

Para ejecutar los tests:

```bash
./gradlew test
```
---
## ⚙️ Ejecución Local

Requisitos: **JDK 17+**
```bash
git clone https://github.com/danacreyes/algo-que-pedir-2025.git
cd backend
```
<img width="429" height="59" alt="image" src="https://github.com/user-attachments/assets/e468cfc0-7007-4bda-8ebc-a80cb7bd4aad" />

Servidor disponible en: [http://localhost:9000](http://localhost:9000)

--- 
## 👩‍💻 Proyecto desarrollado en equipo
- Catalina Correa
- Nicolas Cernadas
- Dana Cossettini Reyes
- Maximiliano Andres Bianchimano
- Fernanda Perez

---
