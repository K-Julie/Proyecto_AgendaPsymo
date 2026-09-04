<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Recuperar Contraseña</title>
        <link rel="stylesheet" href="css/registrarusuario.css">
    </head>
    
    <body>
        <header>
            <h1>Recuperar Contraseña</h1>
        </header>
        
        <main>
            <!-- CONTENEDOR DEL FORMULARIO--> 
            <div class="contenedorFormulario">
                <h2>Ingresa tus datos para recuperar tu contraseña</h2>
                
                <!--FORMULARIO-->
                <form action="RecuperarContrasenaServlet" method="post" class="formularioRegistro">
                    <div class="full">
                        <label>Número de documento:</label>
                        <input type="text" name="numeroDocumento" required>
                    </div>
                    
                    <div class="full">
                        <label>Correo electrónico:</label>
                        <input type="email" name="correo" required>
                    </div>
                    
                    <button type="submit" class="btnRegistrar">Recuperar contraseña</button>
                </form>
                
                <% if (request.getAttribute("mensajeError") != null) { %>
                    <p style="color:red; text-align:center; margin-top:1rem;">
                        <%= request.getAttribute("mensajeError") %>
                    </p>
                <% } %>
                
            </div>
        </main>
    </body>
</html>
