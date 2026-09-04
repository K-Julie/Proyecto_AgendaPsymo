/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controladores;

import dao.CitaDAO;
import modelo.Cita;
import java.io.IOException;
import java.sql.Timestamp;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/CitaServlet")

public class CitaServlet extends HttpServlet {
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
            
        //Obtener los datos del formulario
        String especialidad = request.getParameter("especialidad");
        String tipoServicio = request.getParameter("tipo_servicio");
        String fecha = request.getParameter("fecha"); //Primero se obtienen los string
        String hora = request.getParameter("hora");

                    
        //Obtener el usuario logueado desde la sesión
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("idUsuario") == null) {
            // Si no hay sesión, redirigir al login
            response.sendRedirect("login.jsp");
            return;
        }
        
        int idConsultante = (int) session.getAttribute("idUsuario");
            
        int idProfesional = 1; //Temporal, luego se obtiene dinamicamente
           
        //Convertir fecha y hora a formato Timestamp
        Timestamp fechaHora = Timestamp.valueOf(fecha + " " + hora + ":00");
            
        //Crear un objeto cita
        Cita cita = new Cita(idConsultante, idProfesional, especialidad, tipoServicio, fechaHora, "agendada");

        //Guardar la cita usando DAO
        CitaDAO citaDAO = new CitaDAO();
        boolean exito = citaDAO.agendarCita(cita);
            
        if (exito) {
            request.setAttribute("mensaje", "Cita agendada correctamente");
            request.getRequestDispatcher("panelconsultante.jsp").forward(request, response);
        } else {
            // Si hubo error:
            request.setAttribute("error", "No se pudo agendar la cita. Intenta más tarde.");
            request.getRequestDispatcher("agendarcita.jsp").forward(request, response);
        }
    }    
}
