/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controladores;

import dao.CitaDAO;           
import modelo.Cita;           
import conexion.ConexionDB;    

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/ConsultarCitaServlet")
public class ConsultarCitaServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        //Verifica si existe una sesión activa
        HttpSession session = request.getSession(false);
        //Si no hay sesión activa , se redirige al Login
        if (session == null || session.getAttribute("idUsuario") == null) {
            response.sendRedirect("login.jsp");
            return;
        }
        
        //Se obtiene el ID del usuario 
        int idConsultante = (int) session.getAttribute("idUsuario");

        // Lista donde se guardará las citas consultadas
        List<Cita> citas = new ArrayList<>();
        
        //Consulta SQL
        String sql = "SELECT c.id_cita, c.id_consultante, c.id_profesional, c.especialidad, c.tipo_servicio, " +
                     "c.fecha_hora, c.estado, p.nombres, p.apellidos, p.consultorio " +
                     "FROM citas c " +
                     "JOIN profesionales p ON c.id_profesional = p.id_profesional " +
                     "WHERE c.id_consultante = ? " +
                     "ORDER BY c.fecha_hora ASC";

        try (Connection con = ConexionDB.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            
            //Asigna el ID del consultante al parametro del SQL
            ps.setInt(1, idConsultante);
            
            // Ejecuta la consulta
            try (ResultSet rs = ps.executeQuery()) {
                // Recorre todos los resultados obtenidos
                while (rs.next()) {
                    int idCita = rs.getInt("id_cita");
                    int idProf = rs.getInt("id_profesional");
                    String especialidad = rs.getString("especialidad");
                    String tipoServicio = rs.getString("tipo_servicio");
                    Timestamp fechaHora = rs.getTimestamp("fecha_hora");
                    String estado = rs.getString("estado");

                    String nombreProfesional = rs.getString("nombres") + " " + rs.getString("apellidos");
                    String consultorio = rs.getString("consultorio");
                    
                    //Creación un objeto Cita con los datos obtenidos
                    Cita cita = new Cita(idCita, idConsultante, idProf, especialidad, tipoServicio,
                                         fechaHora, estado, nombreProfesional, consultorio);
                    
                    //Agregamos cada cita a la Lista
                    citas.add(cita);
                }
            }
            // Se envia la Lista de citas al JSP
            request.setAttribute("citas", citas);
            request.getRequestDispatcher("consultarcita.jsp").forward(request, response);

        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("error", "Error al consultar tus citas. Intenta nuevamente.");
            request.getRequestDispatcher("consultarcita.jsp").forward(request, response);
        }
    }
}
