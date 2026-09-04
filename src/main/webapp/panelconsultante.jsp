<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page session="true" %>

<%
    // Recuperar nombre de usuario desde la sesión
    String nombreUsuario = (String) session.getAttribute("nombreUsuario");
    String rol = (String) session.getAttribute("rol");

    // Si no hay sesión activa, redirigir al login
    if (nombreUsuario == null || rol == null) {
        response.sendRedirect("login.jsp");
        return;
    }
%>

<!DOCTYPE html>
<html lang="es">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Panel del Consultante</title>
        <link rel="stylesheet" href="css/panelconsultante.css">
    </head>
    
    <body>
        <header>
            <div class="logoBienvenido">
                <img src="imagenes/Logo_centro.png" alt="Logo Centro Armonía" class="logo">
                <h1>Bienvenido(a) <%= nombreUsuario %></h1>
            </div>
            
            <!--Cerrar sesión-->
            <a href="logout" class="cerrarSesion">
                <img src="imagenes/cerrar-sesion.png" alt="Salir" class="iconoSalir">
                Cerrar sesión
            </a>
        </header>
            
        <main>
            <!-- CARD 1: CONSULTAR CITAS -->
            <a href="ConsultarCitaServlet" class="card card1">
               <img src="imagenes/consultar.png" alt="Icono Citas">
               <h2>Consultar mis citas</h2>
               <p>Aquí podrás revisar y cancelar tus citas programadas.</p>
            </a>
            
            <!-- CARD 2: AGENDAR NUEVA CITA -->
            <a href="agendarcita.jsp" class="card card2">
                <img src="imagenes/agendar.png" alt="Icono Agendar">
                <h2>Agendar nueva cita</h2>
                <p>Programa una nueva cita con tu especialista.</p>
            </a>
        </main>
            
        <footer>
            <img src="imagenes/AgendaPsymo.png" alt="Logo App">
        </footer>
    </body>
</html>

