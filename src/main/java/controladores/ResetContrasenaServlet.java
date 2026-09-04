package controladores;

import dao.UsuarioDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/ResetContrasenaServlet")
public class ResetContrasenaServlet extends HttpServlet {
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        //Datos enviados desde el formulario
        String idParam = request.getParameter("usuarioId");
        String nueva = request.getParameter("nuevaContrasena");
        
        // Validación básica: si nunca se pasó por RecuperarContrasenaServlet
        if (idParam == null || idParam.isEmpty()) {
            request.setAttribute("mensajeError", "ID de usuario no proporcionado. Primero debes pasar por la recuperación.");
            request.getRequestDispatcher("/resetcontrasena.jsp").forward(request, response);
            return; //Detener ejecución
        }

        try {
            //Convertir el ID recibido a número (Todo lo que llega desde un formulario HTML/JSP siempre llega como String)
            int idUsuario = Integer.parseInt(idParam);
            UsuarioDAO dao = new UsuarioDAO();
            //Intentar actualizar la contraseña en BD
            boolean actualizado = dao.actualizarContrasena(idUsuario, nueva);

            if (actualizado) {
                request.setAttribute("mensajeExito", "Contraseña actualizada correctamente.");
            } else {
                request.setAttribute("mensajeError", "No se pudo actualizar la contraseña.");
            }
        } catch (NumberFormatException e) {
            // Si el ID no es númerico
            request.setAttribute("mensajeError", "ID de usuario inválido.");
        }
        //Al mismo JSP para mostrar el resultado
        request.getRequestDispatcher("/resetcontrasena.jsp").forward(request, response);
    }
}
