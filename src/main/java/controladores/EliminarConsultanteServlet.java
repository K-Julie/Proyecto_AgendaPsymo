package controladores;

import dao.UsuarioDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/EliminarConsultanteServlet")
public class EliminarConsultanteServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // Obtener el ID del usuario a eliminar
        int id = Integer.parseInt(request.getParameter("id"));
        //Instancia del DAO para hacer la eliminación en la BD
        UsuarioDAO dao = new UsuarioDAO();
        //Llamar al metodo que elimina al usuario en la BD
        boolean eliminado = dao.eliminarConsultante(id); // devolver true/false
        // Redirige al panel de búsqueda con parámetro : 1= Exito , 0 = Error
        response.sendRedirect("BuscarConsultanteServlet?eliminado=" + (eliminado ? "1" : "0"));
    }
}
