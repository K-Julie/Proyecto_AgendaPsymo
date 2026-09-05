# AgendaPsymo
Sistema web de agendamiento de citas en salud mental (Psicología y Psiquiatría)

## Stack
Java - Servlets - JSP - MySQL - Apache Tomcat - Maven

## Funcionalidades
- **Admin** : Crear, editar y eliminar consultantes
- **Consultante(Paciente)** : Agendar, cancelar y revisar sus citas. Registro , recuperación de contraseña.

## ¿Cómo correrlo?
1. Clonar el repo
2. Importar `Database/agendamiento.sql` en MySQL
3. `mvn clean package`
4. Copiar el `.war` de `target/` a `webapps/` de Tomcat
5. `http://localhost:8080/AgendaPsymo`

## Credenciales de demo

**Admin**: 
*Usuario* : CC | 6324210 
*Contraseña* : 123456

**Consultantes**

 *Usuario* : CC | 2930029
 *Contraseña* : 345678
 
*Usuario* : CC | 1010145002
*Contraseña* : jeffer
 
