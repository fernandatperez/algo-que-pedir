# Algo que Pedir – Sistema de Gestión de Pedidos
 
Aplicación web para la gestión de pedidos gastronómicos, compuesta por múltiples clientes que consumen una misma API REST.
 
El sistema contempla dos perfiles diferenciados:
 
- 👤 Cliente (Frontend en React + TypeScript)
- 🏪 Restaurante / Local (Frontend en Svelte)
- 🧠 Backend centralizado en Kotlin + Spring Boot
Este proyecto demuestra la integración de múltiples interfaces trabajando sobre un backend unificado.
 
---
## 🎯 Objetivo Académico
 
*Trabajo práctico de Algoritmos III*
**Tecnicatura en Programación Informática – UNSAM (2C 2025)**
 
El proyecto integra conceptos de:
- Arquitectura en capas
- Patrones de diseño
- Testing
- Integración multi-cliente con backend centralizado
- Separación de responsabilidades
---
 
## 🏗 Arquitectura General
 
```bash
Cliente (React) Restaurante (Svelte)
↓ ↓
API REST
↓
Backend (Kotlin + Spring Boot)
```
Ambos clientes consumen los mismos endpoints, pero exponen funcionalidades específicas según el rol del usuario.
 
---
 
## 📂 Estructura del Proyecto
 
Este repositorio unifica los tres módulos del sistema (originalmente desarrollados en repos separados) en un solo lugar, conservando el historial completo de cada uno:
 
```bash
algo-que-pedir/
│
├── backend/            → API REST (Kotlin + Spring Boot)
├── frontend-usuario/   → Vista Cliente (React + TypeScript)
├── frontend-local/     → Vista Restaurante (Svelte)
```
 
---
 
## 👤 Frontend Cliente (React) — `/frontend-usuario`
 
Aplicación desarrollada con React + TypeScript orientada al usuario final.
 
### Funcionalidades
 
- Registro e inicio de sesión
- Visualización del menú
- Creación de pedidos
- Consulta de pedidos realizados
- Seguimiento de estado del pedido
---
 
## 🏪 Frontend Restaurante (Svelte) — `/frontend-local`
 
Interfaz administrativa desarrollada en Svelte destinada al local gastronómico.
 
### Funcionalidades
- Visualización de pedidos recibidos
- Cambio de estado del pedido (pendiente → preparado)
- Gestión de ingredientes
- Edición de platos
- Administración del perfil del local
---
 
## 🧠 Backend (Kotlin + Spring Boot) — `/backend`
 
API REST encargada de la lógica de negocio y gestión de datos.
 
### Características
 
- Arquitectura en capas (Controller → Service → Repository → Model)
- DTOs para separación de responsabilidades
- Manejo global de excepciones
- Tests unitarios e integración
- Persistencia en memoria (sin base de datos)
---
 
## 🎥 Demo del Sistema
 
1. Inicio sesión como cliente y restaurante
2. Cambio preferencias del cliente
3. Cliente crea pedido (React)
4. Restaurante visualiza pedido (Svelte)
5. Restaurante cambia estado
6. Cliente ve actualización reflejada y puede calificar
7. Restaurante tiene nueva reseña
https://github.com/user-attachments/assets/6bb5f0e9-4651-408e-a320-2dfaffbc80aa
 
# 🛠 Stack Tecnológico
 
<p align="center"> <img src="https://img.shields.io/badge/Kotlin-7F52FF?style=for-the-badge&logo=kotlin&logoColor=white" /> <img src="https://img.shields.io/badge/Spring_Boot-6DB33F?style=for-the-badge&logo=springboot&logoColor=white" /> <img src="https://img.shields.io/badge/React-20232A?style=for-the-badge&logo=react&logoColor=61DAFB" /> <img src="https://img.shields.io/badge/Svelte-FF3E00?style=for-the-badge&logo=svelte&logoColor=white" /> <img src="https://img.shields.io/badge/TypeScript-007ACC?style=for-the-badge&logo=typescript&logoColor=white" /> </p>
---
 
## 📌 Información Importante
 
Para ejecutar correctamente cada módulo del sistema, se recomienda revisar los README específicos de cada subproyecto:
 
- [Backend](./backend/README.md) → Instrucciones para correr la API REST (Kotlin + Spring Boot)
- [Frontend Cliente](./frontend-usuario/README.md) → Instrucciones para ejecutar la vista Cliente (React + TypeScript + Vite)
- [Frontend Restaurante](./frontend-local/README.md) → Instrucciones para ejecutar la vista Restaurante (Svelte)
Cada uno contiene:
- Requisitos previos
- Comandos de instalación
- Variables de entorno necesarias
- Puertos utilizados
⚠️ Es importante iniciar primero el **backend**, y luego los frontends.
 
---
## 👩‍💻 Proyecto desarrollado en equipo
- Catalina Correa
- Nicolas Cernadas
- Dana Cossettini Reyes
- Maximiliano Andres Bianchimano
- Fernanda Perez
---
