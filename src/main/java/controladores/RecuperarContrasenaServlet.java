package controladores;

import dao.UsuarioDAO;
import modelo.Usuario;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/RecuperarContrasenaServlet")
public class RecuperarContrasenaServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        //Obtener los datos enviados desde el formulario
        String numeroDocumento = request.getParameter("numeroDocumento");
        String correo = request.getParameter("correo");

        UsuarioDAO dao = new UsuarioDAO();
        Usuario usuario = dao.buscarPorDocumentoYCorreo(numeroDocumento, correo);

        if (usuario != null) {
            request.setAttribute("usuarioId", usuario.getIdUsuario()); //Si existe se pasa el ID al JSP
            request.getRequestDispatcher("/resetcontrasena.jsp").forward(request, response);
        } else {
            request.setAttribute("mensajeError", "No se encontró un usuario con esos datos.");
            request.getRequestDispatcher("/recuperarcontrasena.jsp").forward(request, response);
        }
    }
}
