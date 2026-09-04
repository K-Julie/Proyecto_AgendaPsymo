/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controladores;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

import dao.CitaDAO;  // DAO actualizado

@WebServlet("/CancelarCitaServlet")
public class CancelarCitaServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Obtener el ID de la cita a cancelar desde el formulario
        int idCita = Integer.parseInt(request.getParameter("idCita"));

        //Crear el DAO
        CitaDAO citaDAO = new CitaDAO();

        //Intentar cancelar la cita en la BD
        boolean cancelada = citaDAO.cancelarCita(idCita);

        //Redireccionar según resultado
        if (cancelada) {
            response.sendRedirect("ConsultarCitaServlet?msg=cancelada");
        } else {
            response.sendRedirect("ConsultarCitaServlet?msg=error");
        }
    }
}


