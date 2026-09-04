<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Registro de Usuario nuevo</title>
        <link rel="stylesheet" href="css/registrarusuario.css">
    </head>
    
    <body>
        <header>
            <h1>Registro de nuevo usuario</h1>
        </header>
        
        <main>
            <!-- CONTENEDOR FORMULARIO -->
            <div class="contenedorFormulario">  
                <h2>Completa los siguientes campos con tus datos</h2>
                
                <!-- FORMULARIO -->
                <form action="RegistrarUsuarioServlet" method="post" class="formularioRegistro">
                    <div>
                        <label>Tipo de documento:</label>
                        <select name="tipoDocumento" required>
                           <option value="">Selecciona</option>
                           <option value="CC">Cédula de ciudadanía</option>
                           <option value="CE">Cédula de extranjería</option>
                           <option value="PAS">Pasaporte</option>
                        </select>
                    </div>
                    
                    <div>
                        <label>Número de documento:</label>
                        <input type="text" name="numeroDocumento" required>
                    </div>
                    
                    <div>
                        <label>Nombres:</label>
                        <input type="text" name="nombres" required>
                    </div>
                    
                    <div>
                        <label>Apellidos:</label>
                        <input type="text" name="apellidos" required> 
                    </div>
                    
                    <div>
                        <label>Correo Electrónico:</label>
                        <input type="email" name="correo" required>
                    </div>
                    
                    <div>
                        <label>Teléfono:</label>
                        <input type="text" name="telefono" required>
                    </div>
                
                    <div> 
                        <label for="fechaNacimiento">Fecha de nacimiento:</label>
                        <input type="date" name="fechaNacimiento" id="fechaNacimiento" required>
                    </div>
                
                    <div>
                        <label>Contraseña:</label>
                        <input type="text" name="contrasena" required>
                    </div>
                    
                    <button type="submit" class="btnRegistrar">Regístrate</button>  
                </form>
            </div>
                
            <% if (request.getAttribute("mensajeError") != null) { %>
                <p style="color:red;"><%= request.getAttribute("mensajeError") %></p>
            <% } else if (request.getAttribute("mensajeExito") != null) { %>
                <p style="color:green;"><%= request.getAttribute("mensajeExito") %></p>
            <% } %>
        
        </main>
            
        <footer>
            <img src="imagenes/Logo_footer.png" alt="Logo Centro Armonía" class="logoFooter">
            <p>©Copyright 2025</p>
        </footer>
            
        <!--IMPORTACIÓN DE LA LIBRERIA SWEET ALERT2-->
        <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
        
        <!--MODALES-->
        <% if (request.getAttribute("mensajeExito") != null) { %>
            <script>
                Swal.fire({
                  title: "¡Registro exitoso!",
                  icon: "success",
                  confirmButtonText: "Aceptar"
                }).then(() => {
                  window.location.href = "login.jsp"; // 🔹 redirige después del mensaje
                });
            </script>
        <% } else if (request.getAttribute("mensajeError") != null) { %>
            <script>
                Swal.fire({
                  title: "Error al registrar",
                  text: "Por favor, intente de nuevo mas tarde",
                  icon: "error",
                  confirmButtonText: "Intentar de nuevo"
                });
            </script>
        <% } %>
                
    </body>   
</html>
