```text
# digitalfix-ms-bff

BFF del proyecto DigitalFix.
```

Así que ese sí hay que dejarlo completo antes de pasar `development → main`.

Entra a:

```cmd
C:\Users\sebai\Documents\DigitalFix\digitalfix-ms-bff
```

y ejecuta primero:

```cmd
git checkout development
git pull origin development
notepad README.md
```

Reemplaza **todo** el README por esto:

````markdown
# DigitalFix - BFF

Backend for Frontend (BFF) del proyecto **DigitalFix**.

Este servicio centraliza la comunicación entre el frontend Angular y los microservicios de dominio, además de aplicar validación de autenticación y autorización mediante JWT emitidos por Microsoft Entra ID.

## Integrantes

- Sebastián Ahumada
- Benjamín Gutiérrez

## Tecnologías utilizadas

- Java 21
- Spring Boot 4
- Spring Security
- OAuth2 Resource Server
- JWT
- Maven
- Docker
- Microsoft Entra ID
- AWS API Gateway

## Responsabilidades del BFF

El BFF actúa como punto intermedio entre el frontend y los microservicios.

Sus principales responsabilidades son:

- Recibir solicitudes desde el frontend.
- Validar tokens JWT.
- Validar roles del usuario.
- Centralizar llamadas hacia Workorders.
- Centralizar llamadas hacia Catalog.
- Evitar que el frontend acceda directamente a los microservicios.
- Transformar y reenviar solicitudes hacia los servicios internos.

## Arquitectura

Flujo principal:

```text
Angular
   |
   v
AWS API Gateway
   |
   v
JWT Authorizer
   |
   v
VPC Link
   |
   v
Network Load Balancer
   |
   v
DigitalFix BFF
   |
   +----------------------+
   |                      |
   v                      v
Workorders             Catalog
:8080                  :8082
   |                      |
   +-----------+----------+
               |
               v
           Oracle Cloud
````

## Puerto

El BFF utiliza el puerto:

```text
8081
```

## Endpoints

### Workorders

```text
GET  /api/bff/workorders
GET  /api/bff/workorders/{id}
POST /api/bff/workorders
PUT  /api/bff/workorders/{id}/status
```

### Catalog

```text
GET /api/bff/catalog/services
```

## Seguridad

El BFF funciona como OAuth2 Resource Server.

Los tokens JWT son emitidos por Microsoft Entra ID.

La aplicación valida:

* Firma del token.
* Emisor.
* Audiencia.
* Expiración.
* Roles del usuario.

Los roles son obtenidos desde el claim:

```text
roles
```

Roles utilizados:

```text
Admin
Supervisor
Cliente
```

## Autorización por roles

Permisos implementados:

| Recurso              | Admin | Supervisor | Cliente |
| -------------------- | ----- | ---------- | ------- |
| Consultar Workorders | Sí    | Sí         | Sí      |
| Crear Workorders     | Sí    | Sí         | Sí      |
| Cambiar estado       | Sí    | Sí         | No      |
| Consultar Catalog    | Sí    | Sí         | No      |

Aunque Angular restringe la interfaz según el rol, el BFF realiza nuevamente la validación de permisos en el backend.

Ejemplo validado:

```text
Cliente → Catalog → 403 Forbidden
```

## Comunicación con microservicios

El BFF consume internamente:

```text
digitalfix-workorders:8080
digitalfix-catalog:8082
```

Los microservicios no se exponen directamente a Internet.

## Docker

El proyecto incluye un:

```text
Dockerfile
```

El BFF es desplegado como contenedor Docker dentro de una instancia AWS EC2.

Nombre del contenedor utilizado:

```text
digitalfix-bff
```

## Variables de configuración

La configuración utiliza variables de entorno para evitar almacenar información sensible en el repositorio.

Entre las variables utilizadas se encuentran:

```text
AZURE_TENANT_ID
AZURE_CLIENT_ID
WORKORDERS_URL
CATALOG_URL
```

Las credenciales y secretos no deben almacenarse en GitHub.

## Compilación

En Windows:

```powershell
.\mvnw.cmd clean package
```

En Linux:

```bash
./mvnw clean package
```

## Ejecución local

```bash
./mvnw spring-boot:run
```

En Windows:

```powershell
.\mvnw.cmd spring-boot:run
```

## Despliegue

El BFF se encuentra desplegado en AWS EC2 mediante Docker.

El acceso externo sigue este flujo:

```text
Internet
→ AWS API Gateway
→ JWT Authorizer
→ VPC Link
→ Network Load Balancer
→ EC2
→ digitalfix-bff
```

El puerto 8081 no se encuentra expuesto directamente a Internet.

## Flujo de trabajo Git

El proyecto utiliza:

```text
main
development
feature/*
```

Flujo:

```text
feature/*
   ↓
Pull Request
   ↓
development
   ↓
Pull Request de release
   ↓
main
```

Las funcionalidades son desarrolladas en ramas independientes y posteriormente integradas mediante Pull Request.

## Estado del proyecto

Actualmente se encuentran implementados y validados:

* Comunicación con Workorders.
* Comunicación con Catalog.
* Validación JWT.
* Validación de roles.
* Autorización en backend.
* Integración con AWS API Gateway.
* Despliegue mediante Docker.
* Ejecución en AWS EC2.
* Restricción de acceso directo desde Internet.
