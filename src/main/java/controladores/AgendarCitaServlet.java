package controladores;

import conexion.ConexionDB;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

@WebServlet("/AgendarCitaServlet")
public class AgendarCitaServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // Obtener datos del formulario
        String strIdProfesional = request.getParameter("idProfesional");
        String strIdConsultante = request.getParameter("idConsultante");
        String fecha = request.getParameter("fecha");
        String hora = request.getParameter("hora");
        String especialidad = request.getParameter("especialidad");
        String tipoServicio = request.getParameter("tipoServicio");

        try {
            // Validaciones básicas
            if (strIdProfesional == null || strIdConsultante == null ||
                strIdProfesional.isEmpty() || strIdConsultante.isEmpty()) {
                response.sendRedirect("agendarcita.jsp?error=true"); // Error : Falta el ID
                return;
            }

            int idProfesional = Integer.parseInt(strIdProfesional);
            int idConsultante = Integer.parseInt(strIdConsultante);

            if (fecha == null || fecha.isEmpty() || hora == null || hora.isEmpty()) {
                response.sendRedirect("agendarcita.jsp?error=true"); // Error: fecha/hora vacía
                return;
            }

            if (especialidad == null) especialidad = "";
            if (tipoServicio == null) tipoServicio = "";

            // Conversión de fecha y hora
            DateTimeFormatter formatoHora = DateTimeFormatter.ofPattern("h:mm a", new Locale("es", "CO"));
            LocalTime horaLocal = LocalTime.parse(hora, formatoHora);
            Timestamp fechaHora = Timestamp.valueOf(LocalDate.parse(fecha).atTime(horaLocal));

            // Conexión e inserción en la BD
            try (Connection con = ConexionDB.getConnection()) {
                if (con == null) {
                    response.sendRedirect("agendarcita.jsp?error=true"); // ️Sin conexión
                    return;
                }

                String sql = "INSERT INTO citas (id_consultante, id_profesional, especialidad, tipo_servicio, fecha_hora, estado) "
                           + "VALUES (?, ?, ?, ?, ?, 'agendada')";

                try (PreparedStatement ps = con.prepareStatement(sql)) {
                    ps.setInt(1, idConsultante);
                    ps.setInt(2, idProfesional);
                    ps.setString(3, especialidad.toLowerCase());
                    ps.setString(4, tipoServicio.toLowerCase());
                    ps.setTimestamp(5, fechaHora);

                    int filas = ps.executeUpdate();

                    if (filas > 0) {
                        // Cita insertada con éxito
                        response.sendRedirect("agendarcita.jsp?exito=true");
                    } else {
                        // Hubo un error, no se agendó
                        response.sendRedirect("agendarcita.jsp?error=true");
                    }
                }

            } catch (SQLException e) {
                e.printStackTrace();
                response.sendRedirect("agendarcita.jsp?error=true"); // Error SQL
            }

        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("agendarcita.jsp?error=true"); // Error general
        }
    }
}
