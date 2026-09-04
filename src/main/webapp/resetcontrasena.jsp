<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Restablecer Contraseña</title>
        <link rel="stylesheet" href="css/registrarusuario.css">
    </head>
    
    <body>
        <header>
            <h1>Restablecer Contraseña</h1>
        </header>
        
        <main>
            <!-- CONTENEDOR FORMULARIO -->
            <div class="contenedorFormulario">
                <h2>Ingresa tu nueva contraseña</h2>
                
                <!-- FORMULARIO -->
                <form action="ResetContrasenaServlet" method="post" class="formularioRegistro">
                    <!-- Campo oculto con el ID del usuario -->
                    <input type="hidden" name="usuarioId" value="<%= request.getAttribute("usuarioId") != null ? request.getAttribute("usuarioId") : "" %>">

                    <div class="full">
                        <label>Nueva contraseña:</label>
                        <input type="password" name="nuevaContrasena" required>
                    </div>

                    <button type="submit" class="btnRegistrar">Actualizar contraseña</button>
                </form>

                <% if (request.getAttribute("mensajeExito") != null) { %>
                    <p style="color:green; text-align:center; margin-top:1rem;">
                        <%= request.getAttribute("mensajeExito") %>
                    </p>
                <% } else if (request.getAttribute("mensajeError") != null) { %>
                    <p style="color:red; text-align:center; margin-top:1rem;">
                        <%= request.getAttribute("mensajeError") %>
                    </p>
                <% } %>

            </div>
        </main>
    </body>
</html>
