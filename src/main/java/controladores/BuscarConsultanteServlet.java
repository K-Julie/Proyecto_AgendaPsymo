/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controladores;

import dao.UsuarioDAO; //Para consultar usuarios en la BD
import modelo.Usuario; 
import java.io.IOException;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/BuscarConsultanteServlet")
public class BuscarConsultanteServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // Obtiene el término de búsqueda ingresado por el usuario (texto del input)
        String termino = request.getParameter("termino");

        UsuarioDAO dao = new UsuarioDAO(); //Crea una instancia del DAO para interactuar con la BD
        List<Usuario> consultantes = dao.buscarConsultantes(termino); //Busca consultantes que coincidan con el término ingresado

        //Envia la lista de Consultantes encontrados hacia el JSP
        request.setAttribute("consultantes", consultantes);
        //Redirige hacia adminpanel.jsp donde se mostrarán los resultados
        request.getRequestDispatcher("adminpanel.jsp").forward(request, response);
    }
}
