<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %> <!-- Clase importada para usar List<> -->
<%@ page import="modelo.Cita" %> <!-- Para que JSP reconozca la clase Cita -->
<%@page import="java.time.format.DateTimeFormatter"%>
<%@page import="java.util.Locale"%>

<%
    // Acceder a la lista de citas enviada desde el Servlet
    List<Cita> citas = (List<Cita>) request.getAttribute("citas");

    // Formateadores para mostrar fecha y hora en español
    DateTimeFormatter formatoFecha = DateTimeFormatter.ofPattern("EEEE d 'de' MMMM yyyy", new Locale("es","ES"));
    DateTimeFormatter formatoHora = DateTimeFormatter.ofPattern("hh:mm a", new Locale("es","ES"));
%>

<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Consultar citas</title>
        <link rel="stylesheet" href="css/consultarcita.css">
    </head>
    
    <body>
        <header>
            <h1>Mis citas</h1>
      
            <!-- Enlace para cerrar sesión -->
            <a href="logout" class="cerrarSesion">
                <img src="imagenes/cerrar-sesion.png" alt="Salir" class="iconoSalir">
                Cerrar sesión
            </a>
        </header>
        
        <main>
            <!-- Contenedor  para REGRESAR -->
            <div class="regresarContenedor">
                <!-- Botón para regresar al panel del consultante -->
                <a href="panelconsultante.jsp" class="regresar">←Regresar</a>
            </div>
            
            <!-- TABLA DE CITAS -->
            <div class="contenedor">
                <table>
                    <thead>
                        <tr>
                               <th>Profesional</th>
                            <th>Especialidad</th>
                            <th>Fecha</th>
                            <th>Hora</th>
                            <th>Consultorio</th>
                            <th>Acción</th>
                        </tr>
                    </thead>
                    
                    <tbody>
                        <% if(citas != null && !citas.isEmpty()) {
                            for(Cita cita : citas) { 
                                String fechaMostrar = cita.getFechaHora().toLocalDateTime().format(formatoFecha);
                                fechaMostrar = fechaMostrar.substring(0,1).toUpperCase() + fechaMostrar.substring(1);
                                String horaMostrar = cita.getFechaHora().toLocalDateTime().toLocalTime().format(formatoHora);
                        %>
                                <tr>
                                    <td><%= cita.getNombreProfesional() %></td>
                                    <td><%= cita.getEspecialidad() %></td>
                                    <td><%= fechaMostrar %></td>
                                    <td><%= horaMostrar %></td>
                                    <td><%= cita.getConsultorio() %></td>
                                    <td>
                                        <%-- Si la cita ya está cancelada, deshabilitar el botón --%>
                                        <% if(!"Cancelada".equalsIgnoreCase(cita.getEstado())) { %>
                                            <form method="post" action="CancelarCitaServlet" class="cancelarForm">
                                                <input type="hidden" name="idCita" value="<%= cita.getIdCita() %>">
                                                <button type="submit" class="btnCancelar">Cancelar</button>
                                            </form>
                                        <% } else { %>
                                            <button type="button" disabled>Cancelada</button>
                                        <% } %>
                                    </td>
                                </tr>
                         <% } 
                        } else { %>
                            <tr>
                                <td colspan="6">No tienes citas agendadas.</td>
                            </tr>
                        <% } %>
                    </tbody>
                </table>
            </div>
        </main>

        <!-- SWEER ALERT2 PARA LOS MODALES -->
        <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>

        <script>
            // Capturar todos los formularios de cancelar
            document.querySelectorAll('.cancelarForm').forEach(form => {
                form.addEventListener('submit', function(e) {
                    e.preventDefault(); // Evitar envío inmediato
                    Swal.fire({
                        title: '¿Estás seguro de cancelar esta cita?',
                        text: "No podrás revertir esta acción",
                        icon: 'warning',
                        showCancelButton: true,
                        confirmButtonText: 'Sí, cancelar',
                        cancelButtonText: 'No',
                    }).then((result) => {
                        if(result.isConfirmed){
                            form.submit(); // Enviar el formulario si confirma
                        }
                    });
                });
            });
            
            // Mostrar mensajes de éxito o error después de redirección
            <% String msg = request.getParameter("msg"); %>
            <% if("cancelada".equals(msg)) { %>
                Swal.fire({
                    icon: 'success',
                    title: '¡Cita cancelada con éxito!',
                    confirmButtonColor: '#7a5bef',
                    confirmButtonText: 'Aceptar'
                });
            <% } else if("error".equals(msg)) { %>
                Swal.fire({
                    icon: 'error',
                    title: 'Error al cancelar la cita',
                    text: 'Ocurrió un problema, intenta nuevamente.',
                    confirmButtonColor: '#7a5bef',
                    confirmButtonText: 'Aceptar'
                });
            <% } %>
        </script>
    </body>
</html>
