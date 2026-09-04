
<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html lang="es">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>AgendaPsymo</title>
        <link rel="stylesheet" href="css/login.css">
    </head>
    
    <body>
        <!--CONTENEDOR PRINCIPAL para dividir el login en dos columnas-->
        <div class="contenedor">
               
            <!--COLUMNA IZQUIERDA -->
            <div class="columnaIzq">
                <header>
                    <div class="encabezado">
                        <img src="imagenes/Logo_centro.png" class="logoArmonia" alt="Logo Centro Armonía">
                        <div class="textoEncabezado">
                            <h2>Portal de Citas</h2>
                            <h1>Bienvenido(a)</h1>
                        </div>
                    </div>
                </header>
                
                <main>
                    <div class="login"> 
                        <h3>Ingresa tus datos para continuar</h3>
                        <!-- FORMULARIO que envía al servelt-->
                        <form method="post" action="LoginServlet">
                            <div class="seleccionar">
                                <select name="tipo_documento" required>
                                    <option value="">Tipo de Documento</option>
                                    <option value="cc">Cédula de ciudadanía</option>
                                    <option value="ce">Cédula de extranjería</option>
                                    <option value="ps">Pasaporte</option>
                                </select>
                            </div>

                            <div class="numerodoc">
                                <input type="text" name="numero_documento" placeholder="Número de documento" required>
                            </div>

                            <div class="contraseña">
                                <!-- Importante recordar que en name es sin Ñ para evitar problemas: "contrasena" -->
                                <input type="password" name="contrasena" placeholder="Contraseña" required>
                            </div>
                            
                            <div class= "recordar">
                                <a href="recuperarcontrasena.jsp">Olvidé mi contraseña</a>
                            </div>
                            
                            <div class= "registrar">
                                <a href="registrarusuario.jsp">Quiero registrarme</a>
                            </div>
                            
                            <input type="submit" value="Ingresar">
                        </form>

                        <!--Mensaje de error si el inicio de sesión falla, desde el servlet-->
                        <% if (request.getAttribute("error") != null) { %>
                            <p style="color:red; text-align:center;">
                                <%= request.getAttribute("error") %>
                            </p>
                        <% } %>
                    </div>
                </main>
            </div>
                    
            <!--COLUMNA DERECHA-->        
            <div class="columnaDere">
                <img src="imagenes/consulta_login.jpg" alt="Imagén terapia psicologica" class="consulta">
            </div> 
        </div> 
    
        <footer>
            <img src="imagenes/Logo_footer.png" alt="Logo Centro Armonía" class="logoFooter">
            <p>©Copyright 2025</p>
        </footer>        
    </body>
</html>
