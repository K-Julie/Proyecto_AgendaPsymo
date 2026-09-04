<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="modelo.Usuario" %>


<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Admin | AgendaPsyMo</title>
    <link rel="stylesheet" href="css/adminpanel.css">
</head>

<body>
<header>
    <div class="logoBienvenido">
        <img src="imagenes/AgendaPsymo.png" class="logoApp">
        <h1>Bienvenido(a), <%= session.getAttribute("nombreUsuario") %></h1>
    </div>

    <a href="logout" class="cerrarSesion">
        <img src="imagenes/cerrar-sesion.png" class="iconoSalir">
        Cerrar sesión
    </a>
</header>

<main>

    <!-- BUSCADOR -->
    <section class="buscadorUsuarios">
        <h1>Gestión de usuarios</h1>

        <!-- BOTÓN PARA REGISTRAR USUARIO -->
        <a href="agregarconsultante.jsp" class="btnAgregar">
            Registrar Usuario
        </a>

        <form action="BuscarConsultanteServlet" method="get">
            <input type="text" name="termino"
                   placeholder="Buscar por nombre, documento o correo">
            <button type="submit">Buscar</button>
        </form>
    </section>

    <!-- TABLA -->
    <section class="tablaUsuarios">
        <table>
            <thead>
            <tr>
                <th>Nombres</th>
                <th>Apellidos</th>
                <th>Documento</th>
                <th>Correo</th>
                <th>Teléfono</th>
                <th>Fecha nacimiento</th>
                <th>Acciones</th>
            </tr>
            </thead>

            <tbody>
            <%
                List<Usuario> consultantes = (List<Usuario>) request.getAttribute("consultantes");

                if (consultantes != null && !consultantes.isEmpty()) {
                    for (Usuario u : consultantes) {
            %>
            <tr>
                <td><%= u.getNombres() %></td>
                <td><%= u.getApellidos() %></td>
                <td><%= u.getNumeroDocumento() %></td>
                <td><%= u.getCorreo() %></td>
                <td><%= u.getTelefono() %></td>
                <td><%= u.getFechaNacimiento() %></td>
                
                <!--ACCIONES-->
                <td class="acciones">
                    <a href="EditarConsultanteServlet?id=<%= u.getIdUsuario() %>">
                        <img src="imagenes/editarUser.png" class="icono-btn"> Editar
                    </a>

                    <a href="EliminarConsultanteServlet?id=<%= u.getIdUsuario() %>"
                       onclick="return confirm('¿Seguro que deseas eliminar este consultante?');">
                        <img src="imagenes/eliminar.png" class="icono-btn"> Eliminar
                    </a>
                </td>
            </tr>
            <%   }
                } else { %>

            <tr>
                <td colspan="7" style="text-align: center;">No se encontraron consultantes</td>
            </tr>

            <% } %>
            </tbody>
        </table>
    </section>

</main>

<!-- MODAL DE ÉXITO -->
<div id="modalExitoEliminar" class="modal">
    <div class="modal-contenido">
        <h2>¡Usuario eliminado correctamente!</h2>
        <button onclick="cerrarModalEliminar()">Aceptar</button>
    </div>
</div>

<!-- MODAL DE ERROR -->
<div id="modalErrorEliminar" class="modal">
    <div class="modal-contenido">
        <h2>Error al eliminar el usuario </h2>
        <button onclick="cerrarModalEliminar()">Aceptar</button>
    </div>
</div>

<script>
function mostrarModal(id) {
    document.getElementById(id).style.display = "flex";
}

function cerrarModalEliminar() {
    document.getElementById('modalExitoEliminar').style.display = "none";
    document.getElementById('modalErrorEliminar').style.display = "none";
    window.location.href = "adminpanel.jsp"; // redirige al panel
}

// Revisar si llegó parámetro de eliminación
const urlParams = new URLSearchParams(window.location.search);
const eliminado = urlParams.get('eliminado');
if (eliminado === "1") {
    mostrarModal('modalExitoEliminar');
} else if (eliminado === "0") {
    mostrarModal('modalErrorEliminar');
}
</script>

</body>
</html>
