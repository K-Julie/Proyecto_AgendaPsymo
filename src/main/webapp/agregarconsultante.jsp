<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Registrar Usuario</title>
        <link rel="stylesheet" href="css/agregarconsultante.css">
    </head>

    <body>
        <h2>Registrar usuario</h2>
        
        <div class="contenedor">
            <form action="AgregarConsultanteServlet" method="post">

                <label>Tipo de Documento:</label>
                <select name="tipoDocumento" required>
                    <option value="CC">Cédula de ciudadanía</option>
                    <option value="CE">Cédula de extranjería</option>
                    <option value="PA">Pasaporte</option>
                </select>
                <br><br> <!-- Salto de línea -->
        
                <label>Número Documento:</label>
                <input type="text" name="numeroDocumento" required>
                <br><br>
                
                <label>Nombres:</label>
                <input type="text" name="nombres" required>
                <br><br>
                
                <label>Apellidos:</label>
                <input type="text" name="apellidos" required>
                <br><br>
        
                <label>Correo:</label>
                <input type="email" name="correo" required>
                <br><br>
                
                <label>Teléfono:</label>
                <input type="text" name="telefono" required>
                <br><br>
                
                <label>Fecha de Nacimiento:</label>
                <input type="date" name="fechaNacimiento" required>
                <br><br>
                
                <label>Contraseña:</label>
                <input type="password" name="contrasena" required>
                <br><br>
                
                <!-- Rol fijo -->
                <input type="hidden" name="rol" value="consultante">
        
                <button type="submit">Registrar</button>
        
            </form>
        </div>
        <br>

        <!-- MODAL DE ÉXITO -->
        <div id="modalExito" class="modal">
            <div class="modal-contenido">
                <h2>¡Usuario registrado exitosamente! 🎉</h2>
                <button onclick="cerrarModal()">Aceptar</button>
            </div>
        </div>

        <script>
            // Función para mostrar el modal
            function mostrarModal() {
                document.getElementById("modalExito").style.display = "flex";
            }
            
            // Función para cerrar el modal y redirigir al adminpanel
            function cerrarModal() {
                document.getElementById("modalExito").style.display = "none";
                window.location.href = "adminpanel.jsp";
            }
        </script>

        <%
            String exito = request.getParameter("exito");
            if ("1".equals(exito)) {
        %>
        <script>
            window.onload = function() {
                mostrarModal();
            }
        </script>
        <% } %>

    </body>
</html>
