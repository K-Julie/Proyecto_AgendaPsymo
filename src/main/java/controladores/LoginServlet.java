/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package controladores;

import java.io.IOException;
import java.sql.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import conexion.ConexionDB;

@WebServlet("/LoginServlet")

public class LoginServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //Obtener los datos del formulario
        String tipoDoc = request.getParameter("tipo_documento");
        String numero  = request.getParameter("numero_documento");
        String pass    = request.getParameter("contrasena"); // sin ñ para evitar inconvenientes
        //Conexión a la BD
        try (Connection con = ConexionDB.getConnection()) {
            
            //Consulta SQL
            String sql = "SELECT id_usuario, nombres, rol FROM usuarios "
                       + "WHERE tipo_documento=? AND numero_documento=? AND contrasena=?";
            PreparedStatement ps = con.prepareStatement(sql);
            //Asigna los valores a los parametros de la consulta
            ps.setString(1, tipoDoc);
            ps.setString(2, numero);
            ps.setString(3, pass);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                int idUsuario = rs.getInt("id_usuario");
                String nombres = rs.getString("nombres");
                String rol = rs.getString ("rol");
                
                //Guardar nombres, id y rol en la sesión
                
                HttpSession session = request.getSession();
                session.setAttribute("nombreUsuario" , nombres);
                session.setAttribute("rol", rol);
                session.setAttribute("idUsuario", idUsuario);
                
               //Tiempo de inactividad para que caduque automátiicamente la sesión
                session.setMaxInactiveInterval(900);
                        
               // Redirección según rol   
               
               if ("administrador".equals(rol)) { //exactamente el valor en mi tabla MySql!!!!
                   response.sendRedirect("adminpanel.jsp");
               } else if ("consultante".equals(rol)){
                   response.sendRedirect("panelconsultante.jsp");  
               } else { //En caso de que el rol no sea válido
                   request.setAttribute("error", "Rol no válido, contacte al administrador");
                   request.getRequestDispatcher("login.jsp").forward(request, response);
               }
               
               } else {
                //Si algún dato es incorrecto:
                request.setAttribute("error", "Documento o contraseña no válidos, por favor intente de nuevo");
                request.getRequestDispatcher("login.jsp").forward(request, response);
            }

        } catch (Exception e) {
            throw new ServletException("Error en la conexión o consulta", e);
        }
    }
}
