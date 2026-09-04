<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="modelo.Usuario" %>
<!DOCTYPE html>
<html lang="es">
    <head>
        <meta charset="UTF-8">
        <title>Editar usuario</title>
        <link rel="stylesheet" href="css/editarconsultante.css">
    </head>
    
    <body>
        
    <%
        Usuario u = (Usuario) request.getAttribute("consultante");
        Boolean exito = (Boolean) request.getAttribute("exito"); // true=exito, false=error, null=nada
    %>
    
        <!-- CONTENEDOR -->
        <div class="contenedor">
            <h2>Editar Usuario</h2>
    
            <!-- FORMULARIO -->
            <form action="EditarConsultanteServlet" method="post">
                <input type="hidden" name="id" value="<%= u.getIdUsuario() %>">
                       
                <label>Nombres:</label>
                <input type="text" name="nombres" value="<%= u.getNombres() %>" required>
                
                <label>Apellidos:</label>
                <input type="text" name="apellidos" value="<%= u.getApellidos() %>" required>
                
                <label>Tipo documento:</label>
                <input type="text" name="tipoDocumento" value="<%= u.getTipoDocumento() %>" required>
                
                <label>Número documento:</label>
                <input type="text" name="numeroDocumento" value="<%= u.getNumeroDocumento() %>" required>
                
                <label>Correo:</label>
                <input type="email" name="correo" value="<%= u.getCorreo() %>" required>
                
                <label>Teléfono:</label>
                <input type="text" name="telefono" value="<%= u.getTelefono() %>">
                
                <label>Fecha nacimiento:</label>
                <input type="date" name="fechaNacimiento" value="<%= u.getFechaNacimiento() %>" required>
                
                <button type="submit">Guardar cambios</button>
            </form>
        </div>

        <!-- MODAL DE ÉXITO -->
        <div id="modalExito" class="modal">
            <div class="modal-contenido">
                <h2>¡Usuario actualizado exitosamente!</h2>
                <button onclick="cerrarModal()">Aceptar</button>
            </div>
        </div>
        
        <!-- MODAL DE ERROR -->
        <div id="modalError" class="modal">
            <div class="modal-contenido">
                <h2>Error al actualizar el usuario</h2>
                <button onclick="cerrarModal()">Aceptar</button>
            </div>
        </div>
            
        <script>
            function mostrarModal(id) {
                document.getElementById(id).style.display = "flex";
            }
            
            function cerrarModal() {
                window.location.href = "adminpanel.jsp"; // Redirige al panel
            }
            
            // Mostrar modal según parámetro pasado desde el Servlet
            <% if (exito != null) { %>
                window.onload = function() {
                    if (<%= exito %>) {
                        mostrarModal('modalExito');
                    } else {
                        mostrarModal('modalError');
                    }
                };
            <% } %>
        </script>
    </body>
</html>
