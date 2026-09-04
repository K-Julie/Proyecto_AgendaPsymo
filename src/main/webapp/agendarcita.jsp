<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.List"%> <!-- Uso de import para usar List<String> -->
<%@ page import="java.time.LocalDate" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<%@ page import="java.util.Locale" %>

<%
    // Obtener resultados enviados desde el Servlet
    List<String> resultados = (List<String>) request.getAttribute("resultados");
%>

<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Agendamiento de citas</title>
        <link rel="stylesheet" href="css/agendarcita.css">
    </head>

    <body>
        <header>
            <h1>Agenda tu cita</h1>
      
            <!--Cerrar sesión-->
            <a href="logout" class="cerrarSesion">
                <img src="imagenes/cerrar-sesion.png" alt="Salir" class="iconoSalir">
                Cerrar sesión
            </a>
        </header>
        
        <main>
            <!-- Para regresar al Panel anterior -->
            <a href="panelconsultante.jsp" class="regresar">←Regresar</a>
            
            <!--CONTENEDOR DE SECCIONES-->
            <div class="contenedorSecciones"> 
                
                <!-- SECCIÓN IZQUIERDA: Formulario para consultar disponibilidad -->
                <div class="seccionIzquierda">
                
                    <!-- FORMULARIO-->
                    <!-- action debe coincidir con el mapping del Servlet que procesará la busqueda -->
                    <form action="DisponibilidadServlet" method="post"> 
                        <h2>Completa la información para tu cita</h2>
                    
                        <!-- Guardar id del usuario logueado,de manera oculta -->
                        <input type="hidden" name="idConsultante" value="${sessionScope.idUsuario}">
                    
                        <!-- Campo Especialidad -->
                        <label for="especialidad">Especialidad</label>
                        <select id="especialidad" name="especialidad" required>
                            <option value="">Selecciona una especialidad</option>
                            <option value="psicologia">Psicología</option>
                            <option value="psiquiatria">Psiquiatría</option>
                        </select>
                    
                        <!-- campo Tipo de Servicio -->
                        <label for="tipoServicio">Tipo de Servicio</label>
                        <select id="tipoServicio" name="tipoServicio" required>
                            <option value="">Selecciona una opción</option>
                            <option value="primera_vez">Primera vez</option>
                            <option value="control">Control</option>
                        </select>
                    
                        <!-- campo Fecha -->
                        <label for="fecha">Fecha:</label>
                        <input type="date" id="fecha" name="fecha">
                    
                        <!--opcion fecha mas cercana-->
                    
                        <div class="fechaCercana">
                            <input type="checkbox" id="fechaCercana" name="fechaCercana">
                            <label for="fechaCercana">Buscar fecha más cercana</label>
                        </div>
                    
                        <!--Botón consultar-->
                        <button type="submit" id="consultar" class="consultar"> Consultar </button>
                    </form>
                </div>
            
                <!--SECCIÓN DERECHA: Resultados de la búsqueda - Citas disponibles -->
                <div class="seccionDerecha">
                    <h2>Disponibilidad</h2>
                
                <!-- Mostrar TABLA -->
                <% if (resultados != null && !resultados.isEmpty()) { %>
                    <table>
                        <thead>
                            <tr>
                                <th>Profesional</th>
                                <th>Fecha</th>
                                <th>Hora</th>
                                <th>Acción</th>
                            </tr>
                        </thead>
                        <tbody>
                        <%
                            for (String fila : resultados) {
                                // Si el resultado es un mensaje (no tiene |)
                                if(!fila.contains("|")) { %>
                                    <tr><td colspan="4"><%= fila %></td></tr>
                                    <%      continue;
                                }

                            String[] datos = fila.split("\\|");
                            if(datos.length < 7) continue;

                                String idProfesional = datos[0];
                                String nombreProfesional = datos[1];
                                String fechaISO = datos[2];
                                String hora = datos[3];
                                String especialidad = datos[4];
                                String tipoServicio = datos[5];
                                String consultorio = datos[6];

                            // Formatear fecha a largo
                            DateTimeFormatter formatoLargo = DateTimeFormatter.ofPattern(
                                "EEEE d 'de' MMMM yyyy", new Locale("es","ES"));
                            String fechaMostrar = LocalDate.parse(fechaISO).format(formatoLargo);
                            
                            // Capitalizar la primera letra (para que aparezca "Miércoles" en lugar de "miércoles")
                            fechaMostrar = fechaMostrar.substring(0,1).toUpperCase() + fechaMostrar.substring(1);
                        %>
                                    
                            <tr>
                                <td><%= nombreProfesional %></td>
                                <td><%= fechaMostrar %></td>
                                <td><%= hora %></td>
                                <td>
                                    <button type="button"
                                        class="btnAgendar"
                                        onclick="mostrarModal('<%= idProfesional %>', '<%= nombreProfesional %>', '<%= fechaISO %>', '<%= fechaMostrar %>', '<%= hora %>', '<%= especialidad %>', '<%= tipoServicio %>', '<%= consultorio %>')">
                                        Agendar cita
                                    </button>
                                </td>
                            </tr>
                            <% } %>
                        </tbody>
                    </table>
                <% } else { %>
                    <p>No hay resultados para mostrar.</p>
                <% } %>
                </div>
            </div>
        </main>
        
        <!-- MODAL DE CONFIRMACIÓN-->
        <div id="modalConfirmacion">
            
            <!--Datos que se mostrarán en el Modal-->
            <h3>¿Deseas confirmar el agendamiento de esta cita?</h3>
            <p><strong>Profesional:</strong> <span id="modalProfesional"></span></p>
            <p><strong>Fecha:</strong> <span id="modalFecha"></span></p>
            <p><strong>Hora:</strong> <span id="modalHora"></span></p>
            <p><strong>Especialidad:</strong> <span id="modalEspecialidadMostrar"></span></p>
            <p><strong>Tipo de servicio:</strong> <span id="modalTipoServicioMostrar"></span></p>
            <p><strong>Consultorio:</strong> <span id="modalConsultorio"></span></p>
            
            <!-- Formulario que enviará los datos al servlet AgendarCitaServlet -->
            <form action="AgendarCitaServlet" method="post">
                <!-- Campos ocultos (no visibles para el usuario) -->
                <input type="hidden" id="modalIdProfesional" name="idProfesional">
                <input type="hidden" id="modalFechaInput" name="fecha">
                <input type="hidden" id="modalHoraInput" name="hora">
                <input type="hidden" id="modalEspecialidad" name="especialidad">
                <input type="hidden" id="modalTipoServicio" name="tipoServicio">       
                <input type="hidden" id="modalConsultorioInput" name="consultorio">
                <input type="hidden" name="idConsultante" value="${sessionScope.idUsuario}">
                <!-- Botones de acción -->
                <button type="submit">¡Sí! Agendar cita</button>
                <button type="button" onclick="cerrarModal()">Cancelar</button>
            </form>
        </div>
                
        <!-- SCRIPT: Controla la apertura y cierre del MODAL -->
        <script>
            // Esta función se ejecuta cuando el usuario hace clic en "Confirmar cita" en la tabla
            function mostrarModal(idProfesional, profesional, fechaISO, fechaMostrar, hora, especialidad, tipoServicio, consultorio) {
                // Mostrar los datos de la cita en el modal
                document.getElementById("modalIdProfesional").value = idProfesional;
                document.getElementById("modalProfesional").innerText = profesional;
                document.getElementById("modalHora").innerText = hora;
                document.getElementById("modalFecha").innerText = fechaMostrar; 
                document.getElementById("modalEspecialidadMostrar").innerText = especialidad;
                document.getElementById("modalTipoServicioMostrar").innerText = tipoServicio;
                document.getElementById("modalConsultorio").innerText = consultorio;
                
                // Rellenar los campos ocultos que van al servlet
                document.getElementById("modalIdProfesional").value = idProfesional;
                document.getElementById("modalFechaInput").value = fechaISO;  
                document.getElementById("modalHoraInput").value = hora;
                document.getElementById("modalEspecialidad").value = especialidad;
                document.getElementById("modalTipoServicio").value = tipoServicio;
                document.getElementById("modalConsultorioInput").value = consultorio;

                // Finalmente, se muestra el modal
                document.getElementById("modalConfirmacion").style.display = "block";
            }

            // Cierra el modal si el usuario cancela
            function cerrarModal() {
                document.getElementById("modalConfirmacion").style.display = "none";
            }
        </script>
        
        <!-- SE AGREGA LIBRERIA SweetAlert2 para modal/mensaje de éxito o error -->
        <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>

        <%
            // Leer los parámetros que vienen desde el Servlet
            String citaAgendada = request.getParameter("exito");
            String citaError = request.getParameter("error");
        %>

        <script>
        <% if ("true".equals(citaAgendada)) { %>
            Swal.fire({
                icon: 'success',
                title: '¡Cita agendada con éxito!',
                confirmButtonColor: '#7a5bef',
                confirmButtonText: 'Aceptar'
            });
        <% } else if ("true".equals(citaError)) { %>
            Swal.fire({
                icon: 'error',
                title: 'Error al agendar la cita',
                text: 'Ocurrió un problema, por favor intenta nuevamente.',
                confirmButtonColor: '#7a5bef',
                confirmButtonText: 'Aceptar'
            });
        <% } %>
        </script>
    </body>
</html>
