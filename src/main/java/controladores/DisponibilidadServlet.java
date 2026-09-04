/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controladores;

import conexion.ConexionDB; //paquete.Clase
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.*;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@WebServlet("/DisponibilidadServlet")

public class DisponibilidadServlet extends HttpServlet {
    
    private static final long serialVersionUID = 1L; //Etiqueta de control de versión interna de Java, identificador de serialización (buena práctica)

    // Configuración de horario
    private static final LocalTime INICIO_MANANA = LocalTime.of(8, 0);
    private static final LocalTime FIN_MANANA = LocalTime.of(12, 0); // Exclusiva en el bucle
    private static final LocalTime INICIO_TARDE = LocalTime.of(14, 0);
    private static final LocalTime FIN_TARDE = LocalTime.of(17,0); // Exclusiva en el bucle
    private static final int PASO_MINUTOS = 60; // Intervalo entre franjas (60 = 1 hora)
    private static final int MAX_DIAS_BUSQUEDA = 30; // Tope para buscar "Fecha más cercana"
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        
            //Obtener parámetros del formulario
            String especialidad = request.getParameter("especialidad");
            String tipoServicio = request.getParameter("tipoServicio");
            String fechaSeleccionada = request.getParameter("fecha");
            boolean fechaCercana = request.getParameter("fechaCercana") != null; 
            
            // Lista para almacenar los resultados de disponibilidad (se mostrará en el JSP)
            List<String> resultados = new ArrayList<>();
            
            // Validación, si no hace ninguna selección de fecha:
            if (((fechaSeleccionada == null || fechaSeleccionada.isEmpty())) && !fechaCercana){
                request.setAttribute("mensaje", "Selecciona una fecha o marca 'Buscar fecha mas cercana'.");
                //Busca el archivo JSP que se enviará al usuario - Le pasa la información, muestra la página con los datos actualizados
                request.getRequestDispatcher("agendarcita.jsp").forward(request, response);
                return;
            }
            
            //Abrir conexión con la BD
            try (Connection con = ConexionDB.getConnection()){
                
                //Obtener lista de profesionales según especialidad seleccionada
                String sqlProf = "SELECT id_profesional, nombres, apellidos, consultorio FROM profesionales WHERE especialidad = ?";
                List<Profesional> profesionales = new ArrayList<>();
            
                try(PreparedStatement ps = con.prepareStatement(sqlProf)) {
                    ps.setString(1, especialidad);
                    try(ResultSet rs = ps.executeQuery()){
                        while (rs.next()){
                            profesionales.add(new Profesional(
                                rs.getInt("id_profesional"),
                                rs.getString ("nombres"),
                                rs.getString ("apellidos"),
                                rs.getString("consultorio")
                            ));
                        }
                    }
                }
                // Determinar fecha de búsqueda 
                LocalDate fechaBusqueda = fechaCercana ? LocalDate.now() : LocalDate.parse(fechaSeleccionada);

                // Formato de hora AM/PM para mostrar
                DateTimeFormatter formatoHora = DateTimeFormatter.ofPattern("h:mm a", new Locale("es", "CO"));

                // SQL para verificar si la cita está ocupada
                String sqlCheck = "SELECT COUNT(*) FROM citas WHERE id_profesional = ? AND fecha_hora = ? AND estado = 'agendada'";

                // Recorrer días y generar disponibilidad
            int diasAvanzados = 0;
            boolean salir = false;
            int LIMITE_RESULTADOS = 10; 
       
                while (diasAvanzados <= MAX_DIAS_BUSQUEDA && !salir){
                    //Saltar domingos
                    if(fechaBusqueda.getDayOfWeek()== DayOfWeek.SUNDAY){
                        fechaBusqueda = fechaBusqueda.plusDays(1);
                        diasAvanzados++;
                        continue;
                    }
                    
                    //Generar las horas/horarios de las citas 
                    List<LocalTime> horasDelDia = generarHorarioDelDia();
                    
                    for (Profesional prof : profesionales) {
                        for (LocalTime hora : horasDelDia) {

                            // Combinar fecha + hora
                            LocalDateTime fechaHoraLocal = LocalDateTime.of(fechaBusqueda, hora);
                            Timestamp ts = Timestamp.valueOf(fechaHoraLocal);

                            // Consultar si está ocupada
                            try (PreparedStatement psCheck = con.prepareStatement(sqlCheck)) {
                            psCheck.setInt(1, prof.id);
                            psCheck.setTimestamp(2, ts);

                            try (ResultSet rsCheck = psCheck.executeQuery()) {
                                rsCheck.next();
                                if (rsCheck.getInt(1) == 0) {
                                    // Agregar a resultados como string separando por '|'
                                    resultados.add(String.join("|",
                                        String.valueOf(prof.id),                // [0] id profesional
                                        prof.nombres + " " + prof.apellidos,   
                                        fechaBusqueda.toString(),               
                                        hora.format(formatoHora),               
                                        especialidad,                           
                                        tipoServicio,                           
                                        prof.consultorio                         // [6] 
                    ));

                    // Limitar resultados si se busca "fecha cercana"
                    if (fechaCercana && resultados.size() >= LIMITE_RESULTADOS) {
                        salir = true;
                        break;
                    }
                }
            }
        }
    }
    if (salir) break;
}
                    
    // Si el usuario eligió fecha exacta, no seguir buscando más días
    if (!fechaCercana) break;

    fechaBusqueda = fechaBusqueda.plusDays(1);
    diasAvanzados++;
}
                    
                //Si no se encuentra horarios disponibles
                if (resultados.isEmpty()){
                    resultados.add ("No se encontraron horarios disponibles en los próximas " +MAX_DIAS_BUSQUEDA+ " días");
                }   

                System.out.println("=== Resultados encontrados ===");
for (String r : resultados) {
    System.out.println(r);
}
                //enviar resultados al JSP
                request.setAttribute("resultados", resultados);
                request.getRequestDispatcher("agendarcita.jsp").forward(request, response);
         
                } catch (SQLException ex) {
                    // Error en BD
                    ex.printStackTrace();
                    resultados.clear();
                    resultados.add("Error al consultar disponibilidad (intenta nuevamente).");
                    request.setAttribute("resultados", resultados);
                    request.getRequestDispatcher("agendarcita.jsp").forward(request, response);
                } 
                
       }        
        // MÉTODO PARA GENERAR LOS HORARIOS DEL DÍA 
        private List<LocalTime> generarHorarioDelDia() {
            List<LocalTime> horasDelDia = new ArrayList<>();

        // Horario mañana: 08:00am hasta antes de 12:00pm
         for (LocalTime t = INICIO_MANANA; t.isBefore(FIN_MANANA); t = t.plusMinutes(PASO_MINUTOS)) { // PASO_MINUTOS para el intervalo: 60 = 1 hora
            horasDelDia.add(t);
        }
        // Horario tarde: 14:00 hasta antes de las 17:00
        for (LocalTime t = INICIO_TARDE; t.isBefore(FIN_TARDE); t = t.plusMinutes(PASO_MINUTOS)) {
            horasDelDia.add(t);
        }
        return horasDelDia;
        }

        // Clase interna para manejar los datos del profesionales
        private static class Profesional {
            int id;
            String nombres;
            String apellidos;
            String consultorio;
        
            Profesional(int id, String nombres, String apellidos, String consultorio) {
                this.id = id;
                this.nombres = nombres;
                this.apellidos = apellidos;
                this.consultorio = consultorio;
            }
        }
}

